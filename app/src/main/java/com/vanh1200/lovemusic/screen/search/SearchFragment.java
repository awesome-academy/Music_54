package com.vanh1200.lovemusic.screen.search;

import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.History;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.local.TrackLocalDataSource;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.screen.search.adapter.HistoryAdapter;
import com.vanh1200.lovemusic.screen.search.adapter.ResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BaseFragment implements View.OnClickListener,
        SearchView.OnQueryTextListener, SearchContract.View {
    private ImageView mImageBack;
    private ImageView mImageMic;
    private SearchView mSearchViewTrack;
    private RecyclerView mRecyclerHistory;
    private RecyclerView mRecyclerResult;
    private List<History> mHistories;
    private HistoryAdapter mHistoryAdapter;
    private ResultAdapter mResultAdapter;
    private SearchContract.Presenter mPresenter;
    private Group mGroupResult;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mImageBack = view.findViewById(R.id.image_back);
        mImageMic = view.findViewById(R.id.image_micro);
        mSearchViewTrack = view.findViewById(R.id.search_view_track);
        mRecyclerHistory = view.findViewById(R.id.recycler_history);
        mRecyclerResult = view.findViewById(R.id.recycler_result);
        mGroupResult = view.findViewById(R.id.group_search);
        mPresenter = new SearchPresenter(TrackRepository.getInstance(
                TrackLocalDataSource.getInstance(getActivity()),
                TrackRemoteDataSource.getInstance()));
        mPresenter.setView(this);
        registerEvent();
        initRecyclerHistory();
        initRecylerTrack();
        fakeDateForHistory();
    }

    private void initRecylerTrack() {
        mResultAdapter = new ResultAdapter(getActivity(), new ArrayList<Track>());
        mRecyclerResult.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerResult.setAdapter(mResultAdapter);
    }

    private void initRecyclerHistory() {

    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {

    }



    private void fakeDateForHistory() {
        mHistories = new ArrayList<>();
        mHistories.add(new History(1, "history 1"));
        mHistories.add(new History(2, "history 2 asdfasdfasdfasdf "));
        mHistories.add(new History(2, "history 2 asdfasdfasdfasdf "));
        mHistories.add(new History(2, "history 2 asdfasdfasdfasdf "));
        mHistories.add(new History(3, "history 3 asdfafsdfas"));
        mHistories.add(new History(3, "history 3 asdfafsdfas"));
        mHistories.add(new History(3, "history 3 asdfafsdfas"));
        mHistories.add(new History(4, "history 4afsdfds"));
        mHistories.add(new History(5, "history 5asdfasdf"));
        mHistories.add(new History(5, "history 5asdfasdf"));
        mHistories.add(new History(5, "history 5asdfasdf"));
        mHistories.add(new History(6, "history 6asdfasdfasdfasfasdfasd"));
        mHistories.add(new History(6, "history 6asdfasdfasdfasfasdfasd"));
        mHistories.add(new History(6, "history 6asdfasdfasdfasfasdfasd"));
        mHistoryAdapter = new HistoryAdapter(getActivity(), mHistories);
        mRecyclerHistory.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerHistory.setAdapter(mHistoryAdapter);
    }

    private void registerEvent() {
        mImageBack.setOnClickListener(this);
        mImageMic.setOnClickListener(this);
        mSearchViewTrack.setOnQueryTextListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                getActivity().onBackPressed();
                break;
            case R.id.image_micro:
                break;
            default:
                break;
        }
    }

    public static SearchFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPresenter.loadResult(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void showHistorySuccess(List<History> histories) {

    }

    @Override
    public void showHistoryFailed(String error) {

    }

    @Override
    public void showResultSuccess(List<Track> tracks) {
        showResultFrame();
        mResultAdapter.setData(tracks);
    }

    @Override
    public void showResultFailed(String error) {
        Toast.makeText(getActivity(), getString(R.string.mess_search_error), Toast.LENGTH_SHORT).show();
    }

    private void showResultFrame() {
        mGroupResult.setVisibility(View.VISIBLE);
    }
}
