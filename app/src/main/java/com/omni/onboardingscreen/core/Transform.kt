import android.view.View

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

//page.apply {
//    if (position <= 1 && position >= -1) {
//        planet.translationX = -position * width
//        name.translationX = -position * width
//        name.translationY = position * height / 5
//        /*
//            Planets and their names move in the opposite direction. So they are stable
//            If the user drags the page right to left :
//            Name: Goes up
//            If the user drags the page left to right :
//            Name: Goes down
//         */
//    }
//}