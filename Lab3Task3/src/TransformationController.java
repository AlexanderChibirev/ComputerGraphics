public class TransformationController {
	
	public static boolean sKeyPressedPlus = false;
	public static boolean sKeyPressedMinus = false;	
	
	private float mCurrentTransformTime = 0;
	
	private final float MIN_TIME = 0.f;
	private final float MAX_TIME = 1.f;
	private final float TRANSFORMATION_SPEED = 0.2f;
	private final float MAX_PAUSE = 1.f;
	
	public enum TransformationState {
		Forward,
		Reverse,
		Pause
	}	
	TransformationState mState = TransformationState.Forward;	
	private float mCurrentPauseTime = 0.f;
	
	public void update(float deltaSeconds) {
		switch (mState)
		{
		case Forward:
			mCurrentTransformTime = Math.min(mCurrentTransformTime + deltaSeconds * TRANSFORMATION_SPEED, MAX_TIME);
			mState = mCurrentTransformTime == MAX_TIME ? TransformationState.Pause : TransformationState.Forward;
			break;
		case Reverse:
			mCurrentTransformTime = Math.max(mCurrentTransformTime - deltaSeconds * TRANSFORMATION_SPEED, MIN_TIME);
			mState = mCurrentTransformTime == MIN_TIME ? TransformationState.Pause : TransformationState.Reverse;
			break;
		case Pause:
			mCurrentPauseTime = Math.min(mCurrentPauseTime + deltaSeconds, MAX_PAUSE);
			if (mCurrentPauseTime == MAX_PAUSE)
			{
				mState = mCurrentTransformTime == MAX_TIME ? TransformationState.Reverse : TransformationState.Forward;
				mCurrentPauseTime = 0.f;
			}
		default:
			break;
		}
	}
	
	public float getCurrentValue() {		
		return mCurrentTransformTime;
	}
	
}
