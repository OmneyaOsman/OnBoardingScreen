import android.view.View
import kotlinx.android.synthetic.main.onboarding_page_item.view.*

/*** parallax transformation to the image
 * to parallax the whole page make parallaxView = page
 */

fun setParallaxTransformation(page: View, position: Float){
    page.apply {
        val parallaxView = this.img
        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                alpha = 1f
            position <= 1 -> { // [-1,1]
                parallaxView.translationX = -position * (width / 2) //Half the normal speed
            }
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                alpha = 1f
        }
    }

}