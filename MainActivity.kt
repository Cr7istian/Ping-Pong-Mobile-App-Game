package com.example.project6

import android.app.Activity
import android.content.res.Resources
import android.media.SoundPool
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import java.util.Timer

class MainActivity : Activity() {
    private lateinit var gameView: GameView
    private lateinit var game: Pong
    private lateinit var detector: GestureDetector
    private lateinit var tv: TextView
    private lateinit var pool: SoundPool
    private var paddleSoundId : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        var width : Int = Resources.getSystem().displayMetrics.widthPixels
        var height : Int = Resources.getSystem().displayMetrics.heightPixels

        // retrieve status bar height
        var statusBarId : Int = resources.getIdentifier( "status_bar_height", "dimen", "android" )
        var statusBarHeight : Int  = resources.getDimensionPixelSize( statusBarId )

        gameView = GameView( this, width, height - statusBarHeight )
        game = gameView.getGame()
        setContentView( gameView )

        var handler : TouchHandler = TouchHandler( )
        detector = GestureDetector( this, handler )
        detector.setOnDoubleTapListener( handler )

        var gameTimer: Timer = Timer()
        var gameTimerTask : GameTimerTask = GameTimerTask(this)
        gameTimer.schedule(gameTimerTask, 0L, GameView.DELTA_TIME.toLong())

        var poolBuilder : SoundPool.Builder = SoundPool.Builder( )
        pool = poolBuilder.build()
        paddleSoundId = pool.load( this, R.raw.hit, 1 )


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(!game.getGameStarted()){
            game.setGameStarted(true)
        }
        if( event != null )
            detector.onTouchEvent( event )
        return super.onTouchEvent(event)
    }

    fun updateModel(){
        if (game.getGameStarted()){
            if(game.getHasLost()){
                game.setGameOver(true)
                if(game.getHitCount() > game.getBestScore()){
                    game.setPreferences(this)
                }
            } else{
                game.moveBall()
            }
        }

        if(game.getPaddleHit()){
            pool.play( paddleSoundId, 1.0f, 1.0f, 0, 0, 1.0f )
            game.setPaddleHit(false)
        }

    }

    fun updateView(){
        gameView.postInvalidate()

    }


    inner class TouchHandler: GestureDetector.SimpleOnGestureListener(){
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {

            updatePaddle(distanceX)
            return super.onScroll(e1, e2, distanceX, distanceY)
        }



        fun updatePaddle(distanceX:Float){
            var first: Int = ( game.getPaddle().left - distanceX ).toInt()
            var sec: Int = (game.getPaddle().right - distanceX ).toInt()
            if(first < 0|| sec > game.getWidth()){

            } else {
                game.getPaddle().left = first
                game.getPaddle().right = sec
            }


        }


    }

}