package com.example.mobility.myplacespoc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Mobility on 10/04/17.
 */

public class ImageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private ArrayList<Images> mImageList;

    public ImageAdapter(FragmentManager fm) {
        super(fm);
    }

    public ImageAdapter(Context baseContext, ArrayList<Images> imagesArrayList, FragmentManager fm) {
        super(fm);
        this.mContext = baseContext;
        this.mImageList = imagesArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInsatnce(mImageList.get(position));
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

   /* public ImageAdapter(Context context, ArrayList<Images> photosList) {

        this.mContext = context;
        this.mImageList = photosList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.images_list,parent,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        Images currentImageObject = mImageList.get(position);
        holder.image.setImageBitmap(currentImageObject.getImage());
    }





    @Override
    public int getItemCount() {
        return mImageList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         ImageView image;
          ViewHolder(View view) {
             super(view);
              image = (ImageView)view.findViewById(R.id.image);
         }
     }*/


}
