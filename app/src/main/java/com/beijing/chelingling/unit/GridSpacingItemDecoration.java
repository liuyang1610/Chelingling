package com.beijing.chelingling.unit;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration
        extends RecyclerView.ItemDecoration {
    private boolean includeEdge;
    private int spacing;
    private int spanCount;

    public GridSpacingItemDecoration(int paramInt1, int paramInt2, boolean paramBoolean) {
        this.spanCount = paramInt1;
        this.spacing = paramInt2;
        this.includeEdge = paramBoolean;
    }

    public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState) {
        int i = paramRecyclerView.getChildAdapterPosition(paramView);
        int j = i % this.spanCount;
        if (this.includeEdge) {
            paramRect.left = (this.spacing - this.spacing * j / this.spanCount);
            paramRect.right = ((j + 1) * this.spacing / this.spanCount);
            if (i < this.spanCount) {
                paramRect.top = this.spacing;
            }
            paramRect.bottom = this.spacing;
        }
        do {
            paramRect.left = (this.spacing * j / this.spanCount);
            paramRect.right = (this.spacing - (j + 1) * this.spacing / this.spanCount);
        } while (i < this.spanCount);
        paramRect.top = this.spacing;
    }
}

