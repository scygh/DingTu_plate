//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android_serialport_api;

import android.util.Log;

import com.printer.io.PortManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public class SerialPort2 extends PortManager {
    private static final String TAG = "SerialPort2";
    private int baudrate;
    private String serialPortPath;
    private int flags;
    private SerialPort serialPortControl;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SerialPort2(String path, int baudrate, int flags) {
        this.serialPortPath = path;
        this.baudrate = baudrate;
        this.flags = flags;
    }

    public SerialPort2() {
    }

    public void setSerialPortPath(String path) {
        this.serialPortPath = path;
    }

    public void setBaudrate(int baudrate) {
        this.baudrate = baudrate;
    }

    public void setFlage(int flags) {
        this.flags = flags;
    }

    public boolean openPort() {
        try {
            File file = new File(this.serialPortPath);
            if (file.exists()) {
                this.serialPortControl = new SerialPort(file, this.baudrate, this.flags);
                this.inputStream = this.serialPortControl.getInputStream();
                this.outputStream = this.serialPortControl.getOutputStream();
                if (this.inputStream != null && this.outputStream != null) {
                    return true;
                }
            }
        } catch (IOException var2) {
            Log.e("SerialPort2", "Open serial port error!", var2);
        }

        return false;
    }

    public void writeDataImmediately(Vector<Byte> data) throws IOException {
        this.writeDataImmediately(data, 0, data.size());
    }

    public void writeDataImmediately(Vector<Byte> data, int offset, int len) throws IOException {
        try {
            if (data.size() > 0) {
                this.outputStream.write(this.convertVectorByteToBytes(data), offset, len);
                this.outputStream.flush();
            }
        } catch (IOException var5) {
            Log.e("SerialPort2", "write data error!", var5);
        }

    }

    public int readData(byte[] bytes) throws IOException {
        return this.inputStream.available() > 0 ? this.inputStream.read(bytes) : 0;
    }

    public boolean closePort() {
        try {
            if (this.inputStream != null) {
                this.inputStream.close();
                this.inputStream = null;
            }

            if (this.outputStream != null) {
                this.outputStream.close();
                this.outputStream = null;
            }

            if (this.serialPortControl != null) {
                this.serialPortControl.close();
                this.serialPortControl = null;
            }

            return true;
        } catch (IOException var2) {
            Log.e("SerialPort2", "Close the steam or serial port error!", var2);
            return false;
        }
    }
}
