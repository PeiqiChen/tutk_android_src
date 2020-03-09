package com.camera.camerawithtutk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.camera.api.AVAPIsClient;
import java.util.concurrent.BlockingDeque;

import static com.camera.camerawithtutk.MainActivity.bq;

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.ViewHolder> {
    private Context context;
    public CameraAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, CameraActivity.class);
            context.startActivity(intent);
        });
        holder.moreInfo.setOnClickListener(view -> bottomShow());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView islive;
        private ImageView moreInfo;
        private SurfaceView video;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            islive = itemView.findViewById(R.id.item_isAlive);
            moreInfo = itemView.findViewById(R.id.item_set);
            video = itemView.findViewById(R.id.item_video);
        }
    }

    private void bottomShow() {
        Dialog dialog = new Dialog(context, R.style.MyDialog);
        // 加载dialog布局view
        View purchase = LayoutInflater.from(context).inflate(R.layout.item_bottom, null);
        ImageView close = purchase.findViewById(R.id.bottom_info_next);
        ImageView reconnect = purchase.findViewById(R.id.bottom_re_next);
        // 设置外部点击 取消dialog
        dialog.setCancelable(true);
        // 获得窗体对象
        Window window = dialog.getWindow();
        // 设置窗体的对齐方式
        window.setGravity(Gravity.BOTTOM);
        // 设置窗体动画
        window.setWindowAnimations(R.style.AnimBottom);
        // 设置窗体的padding
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setContentView(purchase);
        dialog.show();
        close.setOnClickListener(view -> {
            AVAPIsClient.controlVideoThread();
            AVAPIsClient.close();
        });
        reconnect.setOnClickListener(view -> {
            AVAPIsClient.startVideoThread(bq);
        });
    }
}
