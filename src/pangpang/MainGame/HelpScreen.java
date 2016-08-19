package pangpang.MainGame;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import jrcengine.Basic.GL_Screen;
import jrcengine.GL.GL_Camera2D;
import jrcengine.GL.GL_SpriteBatcher;
import jrcengine.GL.GL_Texture;
import jrcengine.GL.GL_TextureRegion;
import jrcengine.Interface.IFace_Game;
import jrcengine.Interface.IFace_Input.TouchEvent;
import jrcengine.Manage.Manage_Assets;
import jrcengine.Math.Math_Overlap;
import jrcengine.Math.Math_Overlap_Rectangle;
import jrcengine.Math.Math_Vector;


public class HelpScreen extends GL_Screen {
    GL_Camera2D guiCam;
    GL_SpriteBatcher batcher;
    Math_Overlap_Rectangle nextBounds;
    Math_Vector touchPoint;
    GL_Texture helpImage;
    GL_TextureRegion helpRegion;    
    
    private int buttons_Tag;
    
    public HelpScreen(IFace_Game game) {
        super(game);
        
        guiCam = new GL_Camera2D(glGraphics, Screen_MainMenu.GAME_WIDTH, Screen_MainMenu.GAME_HEIGHT);
        nextBounds = new Math_Overlap_Rectangle(400, 240, 60, 60);
        touchPoint = new Math_Vector();
        batcher = new GL_SpriteBatcher(glGraphics, 1);
        buttons_Tag = 24;
    }
    
    @Override
    public void resume() {
        helpImage = new GL_Texture(glGame, "help00.png" );
        helpRegion = new GL_TextureRegion(helpImage, 0, 0, Screen_MainMenu.GAME_WIDTH, Screen_MainMenu.GAME_HEIGHT);
    }
    
    @Override
    public void pause() {
        helpImage.dispose();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
            
            if(event.type == TouchEvent.TOUCH_UP)
            {
            	
                if(Math_Overlap.pointInRectangle(nextBounds, touchPoint)
                		&& 37 ==buttons_Tag) {
                    //Assets.playSound(Assets.clickSound);
                	buttons_Tag = 24;
                    game.setScreen(new HelpScreen2(game));
                    return;
                }
                
                this.buttons_Tag = 24;
            }
            
            if (event.type == TouchEvent.TOUCH_DOWN) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (Math_Overlap.pointInRectangle(nextBounds, touchPoint)) {

					this.buttons_Tag = 37;
					return;
				}
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        batcher.beginBatch(helpImage);
        batcher.drawSprite(Screen_MainMenu.GAME_WIDTH/2, Screen_MainMenu.GAME_HEIGHT/2, Screen_MainMenu.GAME_WIDTH, Screen_MainMenu.GAME_HEIGHT, helpRegion);
        batcher.endBatch();
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Manage_Assets.texture.get(buttons_Tag));          
        batcher.drawSprite(430, 270, 60, 60, Manage_Assets.textureRegion.get(buttons_Tag));
        batcher.endBatch();
        
        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void dispose() {
    }
}
