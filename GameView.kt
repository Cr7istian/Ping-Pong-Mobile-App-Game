package com.example.project6

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import android.widget.TextView

class GameView : View {
    private lateinit  var paint: Paint
    private var width: Int = 0
    private var height: Int = 0
    private lateinit var game: Pong
    private lateinit var paddle: Rect


    constructor(context: Context, width: Int, height: Int): super( context){
        this.width = width
        this.height = height

        paint = Paint()
        paint.color = Color.BLACK
        paint.isAntiAlias = true

        paint.strokeWidth = 20.0f
        paint.textSize = 40.0f





        game = Pong(context,Point(width - width/2, 20), 20, Math.PI.toFloat()/4, width * .0005f)
        game.setDeltaTime(DELTA_TIME)
        game.setWidth(width)
        game.setHeight(height)
        game.setPaddle(Rect(width * 3/7, height + 30, width * 4/7 , height))


    }

    fun getGame(): Pong{
        return game
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!game.getGameOver()){
            var cx : Float = game.getBallCenter().x.toFloat( )
            var cy : Float = game.getBallCenter().y.toFloat( )
            canvas.drawCircle( cx, cy, game.getBallRadius().toFloat(), paint )
        } else{
            if(game.getHitCount() > game.getBestScore()){
                canvas.drawText("Final score: ${game.getHitCount()}", 0f, 50f, paint)
                canvas.drawText("New best score!!", 0f, 100f, paint)
            } else {
                canvas.drawText("Final score: ${game.getHitCount()}", 0f, 50f, paint)
                canvas.drawText("NOT your new best score", 0f, 100f, paint)
            }


        }

        canvas.drawRect(game.getPaddle().left.toFloat(),game.getPaddle().top.toFloat(), game.getPaddle().right.toFloat(), game.getPaddle().bottom.toFloat(),paint )

    }

    companion object {
        const val DELTA_TIME: Int = 30
    }
}