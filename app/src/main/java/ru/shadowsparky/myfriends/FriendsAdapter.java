package ru.shadowsparky.myfriends;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MainViewHolder> {
    VKUsersArray users;

    public FriendsAdapter(VKUsersArray users) {
        this.users = users;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_friend_element, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Log.println(Log.DEBUG, "MAIN_TAG", getItemCount() + "");
        ICallbacks.IDownloadImage callback = (image) -> {
            if (image != null) {
                holder.userImage.setImageBitmap(image);
            }
            holder.imageProgress.setVisibility(View.GONE);
        };
        VKApiUserFull currentUser = users.get(position);
        if (currentUser.photo_200 != null) {
            ImageDownloader downloader = new ImageDownloader(callback);
            downloader.execute(currentUser.photo_200);
        }
        holder.userFI.setText(currentUser.first_name + " " + currentUser.last_name);
    }

    public void initDownloadImageCallback(MainViewHolder holder) {
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImage;
        private TextView userFI;
        private ProgressBar imageProgress;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.User_Mini_Photo);
            userFI = itemView.findViewById(R.id.User_FI);
            imageProgress = itemView.findViewById(R.id.ImageDownloadingProgress);
        }
    }

}
