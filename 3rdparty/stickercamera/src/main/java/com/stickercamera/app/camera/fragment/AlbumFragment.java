package com.stickercamera.app.camera.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.stickercamera.R;
import com.stickercamera.app.camera.adapter.GalleryAdapter;
import com.stickercamera.app.camera.ui.AlbumActivity;
import com.stickercamera.app.camera.ui.CropPhotoActivity;
import com.stickercamera.app.camera.util.Crop;
import com.stickercamera.app.model.PhotoItem;

import java.util.ArrayList;

/**
 * @author tongqian.ni
 */
public class AlbumFragment extends Fragment {
    private ArrayList<PhotoItem> photos = new ArrayList<PhotoItem>();

    public boolean isCrop = false;
    public double scaleSize=0;

    public AlbumFragment() {
        super();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static Fragment newInstance(ArrayList<PhotoItem> photos) {
        Fragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putSerializable("photos", photos);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(ArrayList<PhotoItem> photos, boolean isCrop) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putSerializable("photos", photos);
        fragment.setArguments(args);
        fragment.isCrop = isCrop;
        return fragment;
    }

    public static Fragment newInstance(ArrayList<PhotoItem> photos, boolean isCrop,double scalesize) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putSerializable("photos", photos);
        fragment.setArguments(args);
        fragment.isCrop = isCrop;
        fragment.scaleSize=scalesize;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_album, null);
        photos = (ArrayList<PhotoItem>) getArguments().getSerializable("photos");
        albums = (GridView) root.findViewById(R.id.albums);
        albums.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                PhotoItem photo = photos.get(arg2);
                if (isCrop) {
                    Uri uri = photo.getImageUri().startsWith("file:") ? Uri.parse(photo.getImageUri()) : Uri.parse("file://" + photo.getImageUri());

                    Intent intent = new Intent(getActivity(), CropPhotoActivity.class);
                    intent.putExtra("cover_scale",scaleSize);
                    intent.setData(uri);
                    getActivity().startActivityForResult(intent, Crop.REQUEST_CROP);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(AlbumActivity.PIC_URL, photo.getImageUri());
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
//				CameraManager.getInst().processPhotoItem(getActivity(), photo);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        albums.setAdapter(new GalleryAdapter(getActivity(), photos));
    }

    private GridView albums;
}
