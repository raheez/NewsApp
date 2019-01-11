package com.example.muhammedraheezrahman.newslisting.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.muhammedraheezrahman.newslisting.R;

public class RootActivity extends AppCompatActivity {

        //region variable_declaration
        int onStartCount = 0;
        //endregion


        //region activity_lifecycle
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            onStartCount = 1;
            if (savedInstanceState == null) // 1st time
            {
                this.overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
            } else // already created so reverse animation
            {
                onStartCount = 2;
            }
        }

        @Override
        protected void onStart() {
            // TODO Auto-generated method stub
            super.onStart();
            if (onStartCount > 1) {
                this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);

            } else if (onStartCount == 1) {
                onStartCount++;
            }
        }
        //endregion


        //region backkey_pressed_method
        @Override
        public void onBackPressed() {
            super.onBackPressed();

        }
        //endregion

}
