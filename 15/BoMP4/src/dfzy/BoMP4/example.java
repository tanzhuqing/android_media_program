package dfzy.BoMP4;

import dfzy.BoMP4.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class example extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		/* ����VideoView���� */
		final VideoView videoView = (VideoView) findViewById(R.id.VideoView01);

		/* �������ŵ�������ť */
		Button PauseButton = (Button) this.findViewById(R.id.PauseButton);
		Button LoadButton = (Button) this.findViewById(R.id.LoadButton);
		Button PlayButton = (Button) this.findViewById(R.id.PlayButton);

		/* װ�ذ�ť�¼� */
		LoadButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				/* ����·�� */
				videoView.setVideoPath("/sdcard/test.mp4");
				/* ����ģʽ-���Ž����� */
				videoView.setMediaController(new MediaController(
						example.this));
				videoView.requestFocus();
			}
		});
		/* ���Ű�ť�¼� */
		PlayButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				/* ��ʼ���� */
				videoView.start();
			}
		});
		/* ��ͣ��ť */
		PauseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				/* ��ͣ */
				videoView.pause();
			}
		});
	}
}
