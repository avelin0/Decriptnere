package com.example.bruno.dekriptnere;

public class OffsetError {
    private int mOffset;
    private float mError;

    public OffsetError(int offset, float error) {
        this.mOffset = offset;
        this.mError = error;
    }

    public int getmOffset() {
        return mOffset;
    }

    public float getmError() {
        return mError;
    }

    public void setmOffset(int mOffset) {
        this.mOffset = mOffset;
    }

    public void setmError(float mError) {
        this.mError = mError;
    }

}
