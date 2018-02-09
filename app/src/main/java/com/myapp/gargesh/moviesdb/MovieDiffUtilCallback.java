package com.myapp.gargesh.moviesdb;

import android.support.v7.util.DiffUtil;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * This class will be used to update the Adapter of the
 * RecyclerView's model and then the view.
 * Created by gargesh on 04/02/18.
 */

class MovieDiffUtilCallback extends DiffUtil.Callback {

    private List<MovieDb> oldList;
    private List<MovieDb> newList;

    public MovieDiffUtilCallback(List<MovieDb> oldList, List<MovieDb> newList) {

        this.setNewList(newList);
        this.setOldList(oldList);
    }


    private List<MovieDb> getOldList() {
        return oldList;
    }

    private void setOldList(List<MovieDb> oldList) {
        this.oldList = oldList;
    }

    private List<MovieDb> getNewList() {
        return newList;
    }

    private void setNewList(List<MovieDb> newList) {
        this.newList = newList;
    }

    @Override
    public int getNewListSize() {
        return getNewList().size();
    }

    @Override
    public int getOldListSize() {
        return  getOldList().size();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return getOldList().get(oldItemPosition).getId() == getNewList().get(newItemPosition).getId();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return getOldList().get(oldItemPosition).getId() == getNewList().get(newItemPosition).getId();
    }
}
