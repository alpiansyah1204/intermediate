package id.whynot.submission3.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.whynot.submission3.View.Detail.DetailActivity
import id.whynot.submission3.databinding.ItemPostRowBinding
import id.whynot.submission3.model.post

class PostAdapter : RecyclerView.Adapter<PostAdapter.ListViewHolder>() {

    private val postItem = ArrayList<post>()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<post>) {
        postItem.clear()
        postItem.addAll(user)
        notifyDataSetChanged()
        Log.d("TAG", "cek $postItem")
    }

    class ListViewHolder(private val binding: ItemPostRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: post) {
            binding.tvItemName.text = post.name
            Glide.with(itemView.context)
                .load(post.photoUrl)
                .centerCrop()
                .dontTransform()
                .apply(RequestOptions().override(600, 600))
                .into(binding.imgItemPhoto)

            itemView.setOnClickListener {
                Log.e("bind: ", "${post.name}")
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_POST, post)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.tvItemName, "username"),
                        Pair(binding.imgItemPhoto, "image")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemPostRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val post = postItem[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = postItem.size

}