package nitimcorp.com.golfstrokesscoreboard.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import nitimcorp.com.golfstrokesscoreboard.R;
import nitimcorp.com.golfstrokesscoreboard.scorecard.Hole;

/**
 * @file        HoleAdapter.java
 * @copyright   (C) 2017 NitimCorp, Inc
 * @brief
 */

public class HoleAdapter extends RecyclerView.Adapter<HoleAdapter.HoleViewHolder> {

    private final Context mContext;
    private final Hole[] mHoles;

    public HoleAdapter(Context context, Hole[] Hole) {
        mContext = context;
        mHoles = Hole;
    }

    @Override
    public HoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hole_list_item, parent, false);
        HoleViewHolder viewHolder = new HoleViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HoleViewHolder holder, int position) {
        holder.bindHole(mHoles[position]);
    }

    @Override
    public int getItemCount() {
        return mHoles.length;
    }

    class HoleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.holeLabel) TextView mHoleLabel;
        @BindView(R.id.strokesTakenLabel) TextView mStrokeToken;
        @BindView(R.id.minusButton) Button mMniusButton;
        @BindView(R.id.plusButton) Button mPlusButton;

        HoleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindHole(final Hole hole) {
            mHoleLabel.setText(hole.getHoleNumber());
            mStrokeToken.setText(MessageFormat.format("{0}", hole.getStrokesTaken()));
            mPlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int updatedStrokeCount = hole.getStrokesTaken() + 1;
                    hole.setStrokesTaken(updatedStrokeCount);
                    mStrokeToken.setText(MessageFormat.format("{0}", updatedStrokeCount));
                }
            });
            mMniusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int updatedStrokeCount = hole.getStrokesTaken() - 1;
                    if (updatedStrokeCount <= 0) { updatedStrokeCount = 0; }
                    hole.setStrokesTaken(updatedStrokeCount);
                    mStrokeToken.setText(MessageFormat.format("{0}", updatedStrokeCount));
                }
            });
        }


    }
}
