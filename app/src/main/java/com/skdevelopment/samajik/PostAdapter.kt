package com.skdevelopment.samajik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skdevelopment.samajik.models.Post
import kotlinx.android.synthetic.main.post_item_layout.view.*

class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener: PostAdapterClick) :
    FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(options) {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userImg : ImageView= itemView.profile_img
        val userName : TextView = itemView.user_name
        val createdTime: TextView = itemView.create_time
        val postText: TextView = itemView.post_text
        val likeBtn: ImageView = itemView.like_btn
        val likeCount: TextView = itemView.like_count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_item_layout, parent, false))
        viewHolder.likeBtn.setOnClickListener {
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        Glide.with(holder.userImg.context).load(model.createdBy.imgUrl).circleCrop().into(holder.userImg)
        holder.userName.text = model.createdBy.userName
        holder.postText.text = model.postText
        holder.likeCount.text = "${model.likedBy.size} Likes"
        holder.createdTime.text = Utils.getTimeAgo(model.time!!)

        val auth = Firebase.auth
        val curUser = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(curUser)

        if(isLiked)
            holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(holder.likeBtn.context, R.drawable.ic_liked))
        else
            holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(holder.likeBtn.context, R.drawable.ic_unliked))
    }

}



interface  PostAdapterClick {
    fun onLikeClicked(postId: String)
}
