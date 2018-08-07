package ru.shadowsparky.myfriends.Adapter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageCacher;
import ru.shadowsparky.myfriends.Utils.ImageDownloader;
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
            endCallback.scrollEndCallback(position);
        }
        VKApiUserFull currentUser = users.get(position);
        ICallbacks.IDownloadImage callback = (image) -> downloadCallbackWorker(image, holder, currentUser);
        userWithEmptyPhotoChecker(currentUser, callback);
        holder.userFI.setText(currentUser.first_name + " " + currentUser.last_name);
    }

    public void downloadCallbackWorker(Bitmap image, MainViewHolder holder, VKApiUserFull currentUser) {
        if (image != null) {
            holder.userImage.setImageBitmap(image);
            saveImageToFile(ImageCacher.getInstance(), currentUser.photo_200, image);
            holder.userImage.setOnClickListener(view -> touchImageCallback.touchImageCallback(currentUser, holder.userImage));
        }
        holder.imageProgress.setVisibility(View.GONE);
    }

    public void saveImageToFile(ImageCacher cacher, String imageName, Bitmap image) {
        if (!cacher.checkFileExists(cacher.getImageName(imageName))) {
            cacher.saveImage(cacher.getImageName(imageName), image);
        }
    }

    public void userWithEmptyPhotoChecker(VKApiUserFull currentUser, ICallbacks.IDownloadImage callback) {
        if (currentUser.photo_200 != null) {
            cachedPhotoChecker(currentUser, callback);
        }
    }

    public void cachedPhotoChecker(VKApiUserFull currentUser, ICallbacks.IDownloadImage callback) {
        ImageCacher instance = ImageCacher.getInstance();
        if (instance.checkFileExists(instance.getImageName(currentUser.photo_200))) {
            callback.downloadImageCallback(instance.getImage(instance.getImageName(currentUser.photo_200)));
            Log.println(Log.DEBUG, "MAIN_TAG", "Cached image loaded");
        } else {
            ImageDownloader downloader = new ImageDownloader(callback);
            downloader.execute(currentUser.photo_200);
            Log.println(Log.DEBUG, "MAIN_TAG", "Image downloaded");
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
