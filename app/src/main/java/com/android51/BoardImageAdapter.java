package com.android51;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BoardImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private Piece[][] lp;
    private Chess.gameState whiteTurn;

    public BoardImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public int[] coordinates = new int[2];
        public ImageView piece;
        public ImageView square;
    }

    public BoardImageAdapter(Context c, Piece[][] listPiece, Chess.gameState whiteMove) {
        mContext = c;
        Context context = c.getApplicationContext();
        mInflater = LayoutInflater.from(context);
        lp = listPiece;
        whiteTurn = whiteMove;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null) {            // if it's not recycled, initialize some attributes

            rowView = mInflater.inflate(R.layout.square, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.square = (ImageView) rowView.findViewById(R.id.square_background);
            viewHolder.square.setImageResource(mThumbIds[position]);
            viewHolder.piece = (ImageView) rowView.findViewById(R.id.piece);

            Piece.PieceNames pieceName = lp[position%8][7-position/8].getName();
            Piece.Colors pieceColor = lp[position%8][7-position/8].getColor();

            switch(pieceColor){
                case White:
                    switch(pieceName){
                        case pawn: viewHolder.piece.setImageResource(R.drawable.wp);
                            break;
                        case rook: viewHolder.piece.setImageResource(R.drawable.wc);
                            break;
                        case bishop: viewHolder.piece.setImageResource(R.drawable.wb);
                            break;
                        case knight: viewHolder.piece.setImageResource(R.drawable.wn);
                            break;
                        case king: viewHolder.piece.setImageResource(R.drawable.wk);
                            break;
                        case queen: viewHolder.piece.setImageResource(R.drawable.wq);
                            break;
                        default: break;
                    }
                    break;
                case Black:
                    switch(pieceName){
                        case pawn: viewHolder.piece.setImageResource(R.drawable.bp);
                            break;
                        case rook: viewHolder.piece.setImageResource(R.drawable.bc);
                            break;
                        case bishop: viewHolder.piece.setImageResource(R.drawable.bb);
                            break;
                        case knight: viewHolder.piece.setImageResource(R.drawable.bn);
                            break;
                        case king: viewHolder.piece.setImageResource(R.drawable.bk);
                            break;
                        case queen: viewHolder.piece.setImageResource(R.drawable.bq);
                            break;
                        default: break;
                    }
            }

            rowView.setTag(viewHolder);

            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.piece.setTag(lp[position/8][7-position%8]);
        }
        return rowView;
    }



    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
    };

}