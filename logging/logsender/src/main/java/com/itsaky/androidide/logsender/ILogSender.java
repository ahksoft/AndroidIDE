package com.itsaky.androidide.logsender;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILogSender extends IInterface {
    int getPid();
    String getPackageName();
    String getId();
    void ping();
    void onDisconnect();
    void startReader(int port);
    
    abstract class Stub extends Binder implements ILogSender {
        public Stub() {
            this.attachInterface(this, "com.itsaky.androidide.logsender.ILogSender");
        }
        
        public static ILogSender asInterface(IBinder obj) {
            if (obj == null) return null;
            IInterface iin = obj.queryLocalInterface("com.itsaky.androidide.logsender.ILogSender");
            if (iin != null && iin instanceof ILogSender) {
                return (ILogSender) iin;
            }
            return new Proxy(obj);
        }
        
        @Override
        public IBinder asBinder() {
            return this;
        }
        
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogSender");
                    int pid = this.getPid();
                    reply.writeNoException();
                    reply.writeInt(pid);
                    return true;
                case 2:
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogSender");
                    String pkg = this.getPackageName();
                    reply.writeNoException();
                    reply.writeString(pkg);
                    return true;
                case 3:
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogSender");
                    String id = this.getId();
                    reply.writeNoException();
                    reply.writeString(id);
                    return true;
                case 4:
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogSender");
                    this.ping();
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogSender");
                    this.onDisconnect();
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogSender");
                    int port = data.readInt();
                    this.startReader(port);
                    reply.writeNoException();
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
        
        private static class Proxy implements ILogSender {
            private IBinder mRemote;
            
            Proxy(IBinder remote) {
                mRemote = remote;
            }
            
            @Override
            public IBinder asBinder() {
                return mRemote;
            }
            
            @Override
            public int getPid() {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogSender");
                    mRemote.transact(1, data, reply, 0);
                    reply.readException();
                    return reply.readInt();
                } catch (RemoteException e) {
                    return 0;
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
            
            @Override
            public String getPackageName() {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogSender");
                    mRemote.transact(2, data, reply, 0);
                    reply.readException();
                    return reply.readString();
                } catch (RemoteException e) {
                    return null;
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
            
            @Override
            public String getId() {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogSender");
                    mRemote.transact(3, data, reply, 0);
                    reply.readException();
                    return reply.readString();
                } catch (RemoteException e) {
                    return null;
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
            
            @Override
            public void ping() {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogSender");
                    mRemote.transact(4, data, reply, 0);
                    reply.readException();
                } catch (RemoteException e) {
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
            
            @Override
            public void onDisconnect() {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogSender");
                    mRemote.transact(5, data, reply, 0);
                    reply.readException();
                } catch (RemoteException e) {
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
            
            @Override
            public void startReader(int port) {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogSender");
                    data.writeInt(port);
                    mRemote.transact(6, data, reply, 0);
                    reply.readException();
                } catch (RemoteException e) {
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
        }
    }
}
