package com.meta.myapplication.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.meta.myapplication.utils.DensityUtil;

import static androidx.annotation.Dimension.DP;

/**
 * 内间距和外间距
 */
public class SpacingDecoration extends RecyclerView.ItemDecoration {

    // 内边距
    @Dimension
    private int mRowSpacing = 0;
    @Dimension
    private int mColumnSpacing = 0;
    // 四个外边距
    @Dimension
    private int mOutLeftSpacing = 0;
    @Dimension
    private int mOutTopSpacing = 0;
    @Dimension
    private int mOutRightSpacing = 0;
    @Dimension
    private int mOutBottomSpacing = 0;

    public SpacingDecoration() {
    }

    public SpacingDecoration(Context context, @Dimension(unit = DP) int inSpacingDp) {
        this(context, inSpacingDp, inSpacingDp, Color.RED);
    }

    public SpacingDecoration(Context context, @Dimension(unit = DP) int rowSpacingDp, @Dimension(unit = DP) int columnSpacingDp) {
        this(context, rowSpacingDp, columnSpacingDp, Color.TRANSPARENT);
    }

    public SpacingDecoration(Context context,
                             @Dimension(unit = DP) int rowSpacingDp,
                             @Dimension(unit = DP) int columnSpacingDp, @ColorInt int decorationColor) {
        mRowSpacing = DensityUtil.INSTANCE.dp2px( rowSpacingDp);
        mColumnSpacing = DensityUtil.INSTANCE.dp2px( columnSpacingDp);

    }

    /**
     * 设置外边距
     */
    public void setOutSpacing(Context context,
                              @Dimension(unit = DP) int leftDp,
                              @Dimension(unit = DP) int topDp,
                              @Dimension(unit = DP) int rightDp,
                              @Dimension(unit = DP) int bottomDp) {
        mOutLeftSpacing =DensityUtil.INSTANCE.dp2px( leftDp);
        mOutTopSpacing = DensityUtil.INSTANCE.dp2px( topDp);
        mOutRightSpacing =DensityUtil.INSTANCE.dp2px( rightDp);
        mOutBottomSpacing = DensityUtil.INSTANCE.dp2px( bottomDp);
    }


    // ---------------------------------------------------------------------------------------------

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State _state) {
        // super 中将 outRect 四边置 0
        super.getItemOffsets(outRect, view, parent, _state);

        // 位置
        int position = parent.getChildAdapterPosition(view);
        // 总数
        int itemCount = parent.getAdapter().getItemCount();
        if (position >= itemCount) {
            return;
        }

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            // 网格
            getItemOffsetForGrid(outRect, (GridLayoutManager) parent.getLayoutManager(), position, itemCount);
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            // 瀑布
            getItemOffsetForStagger(outRect, view, (StaggeredGridLayoutManager) parent.getLayoutManager(), position, itemCount);
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            // 线性
            getItemOffsetsForLinear(outRect, (LinearLayoutManager) parent.getLayoutManager(), position, itemCount);
        }
    }

    /**
     * 处理线性布局
     */
    private void getItemOffsetsForLinear(@NonNull Rect outRect, LinearLayoutManager lm, int position, int count) {
        if (lm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            // 横向
            outRect.top = mOutTopSpacing;
            outRect.bottom = mOutBottomSpacing;
            // 第一个
            if (0 == position) {
                outRect.left = mOutLeftSpacing;
            }
            // 内边距
            if (position > 0) {
                outRect.left = mColumnSpacing;
            }
            // 最后一个（只有一项item时，即是第一个也是最后一个）
            if (position == count - 1) {
                outRect.right = mOutRightSpacing;
            }
        } else {
            // 纵向
            outRect.left = mOutLeftSpacing;
            outRect.right = mOutRightSpacing;
            // 第一个
            if (0 == position) {
                outRect.top = mOutTopSpacing;
            }
            // 内边距
            if (position > 0) {
                outRect.top = mRowSpacing;
            }
            // 最后一个（只有一项item时，即是第一个也是最后一个）
            if (position == count - 1) {
                outRect.bottom = mOutBottomSpacing;
            }
        }
    }

    /**
     * 处理网格布局
     */
    private void getItemOffsetForGrid(@NonNull Rect outRect, @NonNull GridLayoutManager lm, int position, int count) {
        int spanCount = lm.getSpanCount();
        int column = position % spanCount;
        getGridItemOffsets(outRect, position, count, column, spanCount, lm.getOrientation());
    }

    /**
     * 处理瀑布流布局
     */
    private void getItemOffsetForStagger(@NonNull Rect outRect, @NonNull View view, @NonNull StaggeredGridLayoutManager lm, int position, int count) {
        int spanCount = lm.getSpanCount();
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int column = lp.getSpanIndex();
        getGridItemOffsets(outRect, position, count, column, spanCount, lm.getOrientation());
    }

    private void getGridItemOffsets(Rect outRect, int position, int count, int column, int spanCount, int orientation) {
        if (0 == orientation) {
            // 横向
            if (position < spanCount) {
                outRect.left = mOutLeftSpacing;
            }
            if (count - position < spanCount) {
                outRect.right = mOutRightSpacing;
            }
            if (position >= spanCount) {
                outRect.left = mColumnSpacing;
            }
            if (0 == column) {
                outRect.top = mOutTopSpacing;
            }
            if (column >= 0 && column < spanCount - 1) {
                outRect.bottom = mRowSpacing / 2; // 误差？
            }
            if (column > 0 && column <= spanCount - 1) {
                outRect.top = mRowSpacing / 2;
            }
            if (column == spanCount - 1) {
                outRect.bottom = mOutBottomSpacing;
            }
        } else {
            // 纵向
            if (position < spanCount) {
                outRect.top = mOutTopSpacing;
            }
            if (position >= spanCount) {
                outRect.top = mRowSpacing;
            }
            if (count - position < spanCount) {
                outRect.bottom = mOutBottomSpacing;
            }
            if (0 == column) {
                outRect.left = mOutLeftSpacing;
            }
            if (column >= 0 && column < spanCount - 1) {
                outRect.right = mColumnSpacing / 2; // 误差？
            }
            if (column > 0 && column <= spanCount - 1) {
                outRect.left = mColumnSpacing / 2;
            }
            if (column == spanCount - 1) {
                outRect.right = mOutRightSpacing;
            }
        }
    }
}
