#!/bin/bash

# OpenCode Integration Setup Script for AndroidIDE
# This script sets up OpenCode as a local service for AndroidIDE integration

set -e

OPENCODE_VERSION="v0.0.55"
INSTALL_DIR="/opt/opencode"
SERVICE_PORT="8080"

echo "Setting up OpenCode integration for AndroidIDE..."

# Check if running as root
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root for system-wide installation"
   exit 1
fi

# Create installation directory
mkdir -p "$INSTALL_DIR"
cd "$INSTALL_DIR"

# Download OpenCode binary
echo "Downloading OpenCode $OPENCODE_VERSION..."
ARCH=$(uname -m)
case $ARCH in
    x86_64)
        BINARY_ARCH="x86_64"
        ;;
    aarch64|arm64)
        BINARY_ARCH="arm64"
        ;;
    armv7l)
        BINARY_ARCH="armv7"
        ;;
    *)
        echo "Unsupported architecture: $ARCH"
        exit 1
        ;;
esac

# Download and install OpenCode
curl -fsSL "https://github.com/opencode-ai/opencode/releases/download/$OPENCODE_VERSION/opencode-linux-$BINARY_ARCH" -o opencode
chmod +x opencode

# Create OpenCode configuration
cat > opencode.json << 'EOF'
{
  "data": {
    "directory": "/opt/opencode/data"
  },
  "providers": {
    "anthropic": {
      "apiKey": "${ANTHROPIC_API_KEY}",
      "disabled": false
    },
    "openai": {
      "apiKey": "${OPENAI_API_KEY}",
      "disabled": false
    }
  },
  "agents": {
    "coder": {
      "model": "claude-3.5-sonnet",
      "maxTokens": 8000
    },
    "android": {
      "model": "claude-3.5-sonnet",
      "maxTokens": 8000
    }
  },
  "shell": {
    "path": "/bin/bash",
    "args": ["-l"]
  },
  "debug": false,
  "autoCompact": true
}
EOF

# Create data directory
mkdir -p /opt/opencode/data

# Create systemd service for OpenCode API server
cat > /etc/systemd/system/opencode-api.service << EOF
[Unit]
Description=OpenCode API Server for AndroidIDE
After=network.target

[Service]
Type=simple
User=root
WorkingDirectory=$INSTALL_DIR
Environment=OPENCODE_CONFIG=$INSTALL_DIR/opencode.json
Environment=OPENCODE_API_PORT=$SERVICE_PORT
ExecStart=$INSTALL_DIR/opencode server --port $SERVICE_PORT
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

# Create OpenCode API wrapper script
cat > opencode-server << 'EOF'
#!/bin/bash

# OpenCode API Server wrapper
# Provides REST API interface for AndroidIDE integration

PORT=${OPENCODE_API_PORT:-8080}
CONFIG=${OPENCODE_CONFIG:-/opt/opencode/opencode.json}

# Start OpenCode in server mode
exec /opt/opencode/opencode server \
    --config "$CONFIG" \
    --port "$PORT" \
    --host "127.0.0.1" \
    --cors-origin "*"
EOF

chmod +x opencode-server

# Enable and start the service
systemctl daemon-reload
systemctl enable opencode-api.service

echo "OpenCode integration setup complete!"
echo ""
echo "To complete the setup:"
echo "1. Set your AI API keys in environment variables:"
echo "   export ANTHROPIC_API_KEY='your-key-here'"
echo "   export OPENAI_API_KEY='your-key-here'"
echo ""
echo "2. Start the OpenCode service:"
echo "   systemctl start opencode-api.service"
echo ""
echo "3. Check service status:"
echo "   systemctl status opencode-api.service"
echo ""
echo "4. OpenCode API will be available at: http://localhost:$SERVICE_PORT"
echo ""
echo "AndroidIDE can now use OpenCode for advanced AI assistance!"
