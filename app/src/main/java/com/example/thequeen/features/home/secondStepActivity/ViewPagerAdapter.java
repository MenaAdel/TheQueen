//package com.example.thequeen.features.home.secondStepActivity;
//
//import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import com.example.thequeen.R;
//import com.example.thequeen.databinding.ItemViewPagerBinding;
//import com.example.thequeen.features.home.Image;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//
//public class ViewPagerAdapter extends PagerAdapter {
//    Context context;
//    List<Image>images;
//    LayoutInflater layoutInflater;
//    ItemClickListenerWithPosition listener;
//    ItemViewPagerBinding binding;
//
//
//    public ViewPagerAdapter(ItemClickListenerWithPosition listener ,Context context) {
//        this.listener = listener;
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    public void setImages(List<Image> images){
//        this.images = images;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
//        if(images != null)
//            return images.size();
//        else
//            return 0;
//    }
//
//    @Override
//    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
//        return view ==  object;
//    }
//
//    @NotNull
//    @Override
//    public Object instantiateItem(@NotNull ViewGroup container, final int position) {
//        LayoutInflater inflater = LayoutInflater.from(container.getContext());
//        binding = DataBindingUtil.inflate(inflater, R.layout.item_view_pager, container, false);
//
//        binding.setImg(images.get(position).getImage());
//
//        ViewPager viewPager = (ViewPager) container;
//        viewPager.addView(binding.getRoot() ,position);
//
//        //listening to image click
//        binding.viewPagerIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemSelected(images.get(position).getProduct_id());
//            }
//        });
//
//        return binding.getRoot();
//    }
//
//    @Override
//    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
//        ViewPager viewPager = (ViewPager) container;
//        View view = (View) object;
//        viewPager.removeView(view);
//    }
//
//    public interface ItemClickListenerWithPosition {
//        void onItemSelected(int productId);
//    }
//}