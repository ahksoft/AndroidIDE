package com.itsaky.androidide.logsender;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILogReceiver extends IInterface {
    void ping();
    void connect(ILogSender sender);
    void disconnect(String packageName, String senderId);
    
    abstract class Stub extends Binder implements ILogReceiver {
        public Stub() {
            this.attachInterface(this, "com.itsaky.androidide.logsender.ILogReceiver");
        }
        
        public static ILogReceiver asInterface(IBinder obj) {
            if (obj == null) return null;
            IInterface iin = obj.queryLocalInterface("com.itsaky.androidide.logsender.ILogReceiver");
            if (iin != null && iin instanceof ILogReceiver) {
                return (ILogReceiver) iin;
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
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogReceiver");
                    this.ping();
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogReceiver");
                    ILogSender sender = ILogSender.Stub.asInterface(data.readStrongBinder());
                    this.connect(sender);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.itsaky.androidide.logsender.ILogReceiver");
                    String packageName = data.readString();
                    String senderId = data.readString();
                    this.disconnect(packageName, senderId);
                    reply.writeNoException();
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
        
        private static class Proxy implements ILogReceiver {
            private IBinder mRemote;
            
            Proxy(IBinder remote) {
                mRemote = remote;
            }
            
            @Override
            public IBinder asBinder() {
                return mRemote;
            }
            
            @Override
            public void ping() {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogReceiver");
                    mRemote.transact(1, data, reply, 0);
                    reply.readException();
                } catch (RemoteException e) {
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
            
            @Override
            public void connect(ILogSender sender) {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogReceiver");
                    data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    mRemote.transact(2, data, reply, 0);
                    reply.readException();
                } catch (RemoteException e) {
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
            
            @Override
            public void disconnect(String packageName, String senderId) {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.itsaky.androidide.logsender.ILogReceiver");
                    data.writeString(packageName);
                    data.writeString(senderId);
                    mRemote.transact(3, data, reply, 0);
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
