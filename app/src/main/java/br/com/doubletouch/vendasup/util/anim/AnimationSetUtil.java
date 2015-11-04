package br.com.doubletouch.vendasup.util.anim;

import android.content.Context;
import android.util.TypedValue;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;

import br.com.doubletouch.vendasup.R;

/**
 * Created by ladairsmiderle on 20/10/2015.
 */
public class AnimationSetUtil {

    public static AnimationSet get( Context context){
        TypedValue tv = new TypedValue();

        int height = 0;

        if ( context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true) ) {

            height = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());

        }

        // -19 porque foi o ajuste fino feito para nivelar a notificação exatamente no topo da tela.
        TranslateAnimation fadeOut = new TranslateAnimation( 0, 0 , 0, height -19 );
        fadeOut.setDuration(400);
        fadeOut.setInterpolator(new BounceInterpolator());
        fadeOut.setFillAfter(true);

        TranslateAnimation fadeIn = new TranslateAnimation( 0, 0 , 0, -200 );
        fadeIn.setDuration(400);
        fadeIn.setInterpolator(new BounceInterpolator());
        fadeIn.setFillAfter(true);
        fadeIn.setStartOffset(5100);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(fadeOut);
        animationSet.addAnimation(fadeIn);

        return animationSet;
    }
}
