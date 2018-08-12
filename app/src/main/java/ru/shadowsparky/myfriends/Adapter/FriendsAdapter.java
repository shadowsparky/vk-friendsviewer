package ru.shadowsparky.myfriends.Adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.shadowsparky.myfriends.R;
import ru.shadowsparky.myfriends.Utils.ICallbacks;
import ru.shadowsparky.myfriends.Utils.ImageCacher;
import ru.shadowsparky.myfriends.Utils.ImageDownloader;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MainViewHolder> {
    public static final int START_POSITION = 0;
    VKUsersArray users;
    ICallbacks.ITouchImage touchImageCallback;
    ICallbacks.IScrollEnd scrollingEndCallback;
    ImageCacher imgCacher = new ImageCacher();

    public FriendsAdapter(VKUsersArray users, ICallbacks.ITouchImage touchImageCallback, ICallbacks.IScrollEnd scrollingEndCallback) {
        this.users = users;
        this.touchImageCallback = touchImageCallback;
        this.scrollingEndCallback = scrollingEndCallback;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_friend_element, parent, false);
        return new MainViewHolder(view);
    }
    public void removeAllData() {
        int TMPMaxRange = users.size();
        users.removeAll(users);
        this.notifyItemRangeRemoved(START_POSITION, TMPMaxRange);
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
            scrollingEndCallback.scrollEndCallback(position);
        }
        VKApiUserFull currentUser = users.get(position);
        ICallbacks.IDownloadImage callback = (image, url) -> downloadCallbackWorker(VKUIHelper.getRoundedCornerBitmap(image, 50, 100), holder, currentUser);
        imgCacher.userWithEmptyPhotoChecker(currentUser.photo_200, callback);
        holder.userFI.setText(currentUser.first_name + " " + currentUser.last_name);
    }

    public void downloadCallbackWorker(Bitmap image, MainViewHolder holder, VKApiUserFull currentUser) {
        if (image != null) {
            holder.userImage.setImageBitmap(image);
            imgCacher.saveImageToFile(currentUser.photo_200, image);
            holder.userImage.setOnClickListener(view -> touchImageCallback.touchImageCallback(currentUser, holder.userImage));
        }
        holder.imageProgress.setVisibility(View.GONE);
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
