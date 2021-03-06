package com.nikesh.jobportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nikesh.jobportal.Model.Chat;
import com.nikesh.jobportal.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static  final  int MSG_TYPE_LEFT=0;
    public static  final  int MSG_TYPE_RIGHT=1;
    private Context context;
    private List<Chat> chats;
    private String imgUrl;

    FirebaseUser firebaseUser;
    public MessageAdapter(Context context, List<Chat> chats, String imgUrl ){
        this.context = context;
        this.chats = chats;
        this.imgUrl=imgUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==MSG_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chats.get(position);

            if(chat.getType().equals("text"))
            {
                holder.mgsImg.setVisibility(View.GONE);
                holder.showMessage.setVisibility(View.VISIBLE);
                holder.showMessage.setText(chat.getMessage());

            }
            else
            {
                holder.mgsImg.setVisibility(View.VISIBLE);
                holder.showMessage.setVisibility(View.GONE);
                Glide.with(context).load(chat.getMessage()).into(holder.mgsImg);
            }

            if(imgUrl.equals("Default"))
            {
                holder.profile.setImageResource(R.drawable.male);
            }
            else {
                Glide.with(context.getApplicationContext()).load(imgUrl).into(holder.profile);
            }

         if(chat.getType()=="image")
        {
            holder.mgsImg.setVisibility(View.VISIBLE);
            holder.showMessage.setVisibility(View.GONE);
            Glide.with(context.getApplicationContext()).load(chat.getMessage()).into(holder.mgsImg);
            if(imgUrl.equals("Default"))
            {
                holder.profile.setImageResource(R.drawable.male);
            }
            else {
                Glide.with(context).load(imgUrl).into(holder.profile);
            }
        }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView showMessage;
        public CircleImageView profile;
        private ImageView mgsImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showMessage=itemView.findViewById(R.id.show_message);
            profile=itemView.findViewById(R.id.profile_image);
            mgsImg=itemView.findViewById(R.id.msgImage);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(chats.get(position).getMsgSender().equals((firebaseUser.getUid())))
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }

    }
}
