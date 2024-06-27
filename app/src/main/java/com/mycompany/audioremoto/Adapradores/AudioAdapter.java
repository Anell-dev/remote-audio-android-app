package com.mycompany.audioremoto.Adapradores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycompany.audioremoto.Clases.AudioItem;
import com.mycompany.audioremoto.R;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {

    private List<AudioItem> audioList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AudioItem audioItem);
    }

    public AudioAdapter(List<AudioItem> audioList, OnItemClickListener listener) {
        this.audioList = audioList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        AudioItem audioItem = audioList.get(position);

        holder.textViewAudioTitle.setText(audioItem.getTitle());
        holder.buttonPlayAudio.setOnClickListener(v -> listener.onItemClick(audioItem));
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    static class AudioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAudioTitle;
        Button buttonPlayAudio;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAudioTitle = itemView.findViewById(R.id.textViewAudioTitle);
            buttonPlayAudio = itemView.findViewById(R.id.buttonPlayAudio);
        }
    }
}