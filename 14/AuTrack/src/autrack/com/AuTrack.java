package autrack.com;

import android.app.Activity;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AuTrack extends Activity {
	
	public static final int MENU_START_ID = Menu.FIRST;
	public static final int MENU_STOP_ID = Menu.FIRST + 1;
	public static final int MENU_EXIT_ID = Menu.FIRST + 2;

	protected PCMAudioTrack m_player;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
  
	public boolean onCreateOptionsMenu(Menu aMenu) {
		boolean res = super.onCreateOptionsMenu(aMenu);

		aMenu.add(0, MENU_START_ID, 0, "START");
		aMenu.add(0, MENU_STOP_ID, 0, "STOP");
		aMenu.add(0, MENU_EXIT_ID, 0, "EXIT");

		return res;
	}

	public boolean onOptionsItemSelected(MenuItem aMenuItem) {
		switch (aMenuItem.getItemId()) {
		case MENU_START_ID: {
			m_player = new PCMAudioTrack();
			m_player.init();
			m_player.start();
		}
			break;
		case MENU_STOP_ID: {
			m_player.free();
			m_player = null;
		}
			break;
		case MENU_EXIT_ID: {
			int pid = android.os.Process.myPid();
			android.os.Process.killProcess(pid);
		}
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(aMenuItem);
	}
}

class PCMAudioTrack extends Thread {
	protected AudioTrack m_out_trk;
	protected int m_out_buf_size;
	protected byte[] m_out_bytes;
	protected boolean m_keep_running;

	final String FILE_PATH = "/sdcard/";
	final String FILE_NAME = "test.wav";

	File file;
	FileInputStream in;
	
	public void init() {
		try {		
			file = new File(FILE_PATH , FILE_NAME);
			file.createNewFile();			
			in = new FileInputStream(file);

//			in.read(temp, 0, length);
			
			m_keep_running = true;

			m_out_buf_size = AudioTrack.getMinBufferSize(44100,
					AudioFormat.CHANNEL_CONFIGURATION_STEREO, // CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT);

			m_out_trk = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
					AudioFormat.CHANNEL_CONFIGURATION_STEREO, // CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT,
					m_out_buf_size,
					AudioTrack.MODE_STREAM);

			m_out_bytes = new byte[m_out_buf_size];

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void free() {
		m_keep_running = false;
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			Log.d("sleep exceptions...\n", "");
		}
	}

	public void run() {
		byte[] bytes_pkg = null;
		m_out_trk.play();
		while (m_keep_running) {
			try {
				in.read(m_out_bytes);
				bytes_pkg = m_out_bytes.clone();
				m_out_trk.write(bytes_pkg, 0, bytes_pkg.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		m_out_trk.stop();
		m_out_trk = null;
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
