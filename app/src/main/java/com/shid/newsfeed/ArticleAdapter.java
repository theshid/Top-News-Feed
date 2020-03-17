package com.shid.newsfeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shid.newsfeed.Models.Article;
import com.shid.newsfeed.Utils.Utils;

public class ArticleAdapter extends PagedListAdapter<Article, ArticleAdapter.ArticleViewHolder> {

    private Context context;
    private Article article;
    private ItemAction mItemOnClickAction;

    public ArticleAdapter( Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ArticleViewHolder holder, int position) {
        final ArticleViewHolder articleViewHolder = holder;
        article = getItem(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(article.getTitle());
        holder.desc.setText(article.getDescription());
        holder.source.setText(article.getSource().getName());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(article.getPublishedAt()));
        holder.published_ad.setText(Utils.DateFormat(article.getPublishedAt()));
        holder.author.setText(article.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemOnClickAction.onClick(v,getItem(position));
            }
        });
    }

    public interface ItemAction {
        void onClick(View view,Article itemArticle);
    }

    public void setItemOnClickAction(ItemAction itemOnClickAction) {
        mItemOnClickAction = itemOnClickAction;

    }

    private static final DiffUtil.ItemCallback<Article> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Article>() {
                @Override
                public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                    return oldItem.getSource().getId().equals(newItem.getSource().getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                    return oldItem == newItem;
                }
            };

    class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView title, desc, author, published_ad, source, time;
        ImageView imageView;
        ProgressBar progressBar;

        public ArticleViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_ad = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.prograss_load_photo);

        }
    }
}
