package ru.shadowsparky.myfriends.Adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.shadowsparky.myfriends.ICallbacks;
import ru.shadowsparky.myfriends.ImageDownloader;
import ru.shadowsparky.myfriends.R;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MainViewHolder> {
    VKUsersArray users;
    ICallbacks.ITouchImage touchImageCallback;
    ICallbacks.IScrollEnd endCallback;

    public FriendsAdapter(VKUsersArray users, ICallbacks.ITouchImage touchImageCallback, ICallbacks.IScrollEnd endCallback) {
        this.users = users;
        this.touchImageCallback = touchImageCallback;
        this.endCallback = endCallback;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_friend_element, parent, false);
        return new MainViewHolder(view);
    }

    public void addData(VKUsersArray nextUsers) {
        users.addAll(nextUsers);
        this.notifyItemChanged(users.size());
    }

    public int maxFriendsCount() {
        return users.getCount();
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            Log.println(Log.DEBUG, "MAIN_TAG", position + " end");
            endCallback.scrollEndCallback(position);
        }
        Log.println(Log.DEBUG, "MAIN_TAG", position + " check");
        VKApiUserFull currentUser = users.get(position);
        ICallbacks.IDownloadImage callback = (image) -> downloadCallbackWorker(image, holder, currentUser);
        userWithEmptyPhotoChecker(currentUser, callback);
        holder.userFI.setText(currentUser.first_name + " " + currentUser.last_name);
    }

    public void downloadCallbackWorker(Bitmap image, MainViewHolder holder, VKApiUserFull currentUser) {
        if (image != null) {
            holder.userImage.setImageBitmap(image);
            holder.userImage.setOnClickListener(view -> touchImageCallback.touchImageCallback(currentUser, holder.userImage));
        }
        holder.imageProgress.setVisibility(View.GONE);
    }

    public void userWithEmptyPhotoChecker(VKApiUserFull currentUser, ICallbacks.IDownloadImage callback) {
        if (currentUser.photo_200 != null) {
            ImageDownloader downloader = new ImageDownloader(callback);
            downloader.execute(currentUser.photo_200);
        }
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
            userImage = itemView.findViewById(R.id.User_Photo);
            userFI = itemView.findViewById(R.id.User_FI);
            imageProgress = itemView.findViewById(R.id.ImageDownloadingProgress);
        }
    }

}
