package leonardo2204.com.materialnotes.controller;

import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.firezenk.audiowaves.Visualizer;

import java.io.IOException;

import butterknife.BindView;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.controller.base.BaseController;

/**
 * Created by leonardo on 9/9/16.
 */

public class AudioController extends BaseController {

    @BindView(R.id.audio_visualizer)
    Visualizer audioVisualizer;
    MediaRecorder mediaRecorder = new MediaRecorder();

    public AudioController() {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile("cabrito");
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.audio_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        audioVisualizer.startListening();
    }
}
