package com.example.project6

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Point
import android.graphics.Rect
import android.icu.text.ListFormatter.Width

class Pong {
    private var deltaTime = 0
    private var ballCenter: Point? = null
    private var ballRadius = 0
    private var ballAngle = 0f
    private var ballSpeed = 0f
    private var width = 0
    private var height = 0
    private var dirX ="r"
    private var dirY = "d"
    private var paddle:Rect? = null
    private var hitCount = 0
    private var hasLost: Boolean = false
    private var gameOver = false
    private var paddleHit = false
    private var gameStarted = false
    private var bestScore :Int = 0




    constructor(context: Context,newBallCenter: Point?, newBallRadius: Int, newBallAngle: Float, newBallSpeed: Float){
        setBallCenter(newBallCenter)
        setBallRadius(newBallRadius)
        setBallAngle(newBallAngle)
        setBallSpeed(newBallSpeed)
        var pref : SharedPreferences = context.getSharedPreferences( context.packageName + "_preferences",
            Context.MODE_PRIVATE )
        setBestScore(pref.getInt(PREFERENCE_SCORE,0))




    }
    fun getBallCenter(): Point {
        return ballCenter!!
    }

    fun getBallRadius(): Int {
        return ballRadius
    }

    fun getGameStarted():Boolean{
        return gameStarted
    }

    fun setGameStarted(b: Boolean){
        gameStarted = b
    }


    fun getBallAngle(): Float {
        return ballAngle
    }

    fun getBallSpeed():Float{
        return ballSpeed
    }

    fun setHeight(newHeight: Int){
        height = newHeight
    }

    fun setWidth(newWidth: Int){
        width = newWidth
    }

    fun getWidth():Int{
        return width
    }

    fun setBallCenter(newBallCenter: Point?){
        if(newBallCenter != null){
            ballCenter = newBallCenter
        }
    }
    fun setGameOver(b: Boolean){
        gameOver = b
    }

    fun setBallRadius(newBallRadius: Int) {
        if (newBallRadius > 0)
           ballRadius = newBallRadius
    }

    fun setBallSpeed(newBallSpeed: Float) {
        if (newBallSpeed > 0)
            ballSpeed = newBallSpeed
    }

    fun setBallAngle(newBallAngle: Float){
        ballAngle = newBallAngle
    }

    fun setDeltaTime(newDeltaTime: Int){
        if(newDeltaTime > 0){
            deltaTime = newDeltaTime
        }
    }

    fun setPaddle(newPaddle: Rect?){
        paddle = newPaddle
    }

    fun getPaddle(): Rect{
        return paddle!!
    }


    fun hitPaddle():Boolean{
        return (ballCenter!!.y + ballRadius >= height) && (ballCenter!!.x > paddle!!.left && ballCenter!!.x < paddle!!.right)
    }

    fun hitSidePaddle():Boolean{
        return ((ballCenter!!.y > paddle!!.top +10  && ballCenter!!.y < paddle!!.bottom - 10) && ((ballCenter!!.x + ballRadius == paddle!!.left + 5 && ballCenter!!.x >= paddle!!.left + ballRadius || ballCenter!!.x - ballRadius == paddle!!.right - 5)))
    }


    fun increaseHitCount(){
        hitCount++
    }

    fun getPaddleHit():Boolean{
        return paddleHit
    }

    fun setPaddleHit(b: Boolean){
        paddleHit = b
    }

    fun getHasLost():Boolean{
        return hasLost
    }

    fun getGameOver():Boolean{
        return gameOver
    }

    fun getHitCount():Int{
        return hitCount
    }

    fun setBestScore(best:Int){
        bestScore = best
    }

    fun getBestScore():Int{
        return bestScore
    }


    fun moveBall(){

         if (ballCenter!!.x - ballRadius < 0) {
             dirX = "r"

         }
         if (ballCenter!!.x + ballRadius  > width) {
             dirX = "l"

         }

         if (ballCenter!!.y - ballRadius  < 0){
            dirY = "d"

        }

        if (ballCenter!!.y + ballRadius >= height) {

            if (hitPaddle()) {
                dirY = "u"
                increaseHitCount()
                paddleHit = true
            }


            if(ballCenter!!.y  > height + 60 ){
                hasLost = true
            }

        }



        if(dirX == "r"){
            if (dirY == "d"){
                ballCenter!!.x += (ballSpeed * Math.cos(ballAngle.toDouble()) * deltaTime).toInt()
                ballCenter!!.y += (ballSpeed * Math.sin(ballAngle.toDouble()) * deltaTime).toInt()
            } else {
                ballCenter!!.x += (ballSpeed * Math.cos(ballAngle.toDouble()) * deltaTime).toInt()
                ballCenter!!.y -= (ballSpeed * Math.sin(ballAngle.toDouble()) * deltaTime).toInt()
            }
        } else {
            if (dirY == "d"){
                ballCenter!!.x -= (ballSpeed * Math.cos(ballAngle.toDouble()) * deltaTime).toInt()
                ballCenter!!.y += (ballSpeed * Math.sin(ballAngle.toDouble()) * deltaTime).toInt()
            } else {
                ballCenter!!.x -= (ballSpeed * Math.cos(ballAngle.toDouble()) * deltaTime).toInt()
                ballCenter!!.y -= (ballSpeed * Math.sin(ballAngle.toDouble()) * deltaTime).toInt()
            }
        }



    }

    fun hitPaddle2():Boolean{
        return paddle!!.intersects(
            ballCenter!!.x - ballRadius, ballCenter!!.y - ballRadius,
            ballCenter!!.x + ballRadius, ballCenter!!.y + ballRadius
        )
    }

    fun setPreferences(context: Context){
        var pref : SharedPreferences = context.getSharedPreferences( context.packageName + "_preferences",
            Context.MODE_PRIVATE )

        var editor : SharedPreferences.Editor = pref.edit()
        editor.putInt(PREFERENCE_SCORE, hitCount )
        editor.commit()
    }


    companion object{
        private const val PREFERENCE_SCORE: String = "score"
    }
}