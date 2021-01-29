package de.ellpeck.rockbottom.api.render.entity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.LivingEntity;
import de.ellpeck.rockbottom.api.entity.emotion.Emotion;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class LivingRenderer<T extends LivingEntity> implements IEntityRenderer<T> {

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer renderer, IWorld world, T entity, float x, float y, int light) {
        this.renderEntity(game, manager, renderer, world, entity, x, y, light);
        this.renderEmotion(game, manager, renderer, world, entity, x, y, light);
    }

    public abstract void renderEntity(IGameInstance game, IAssetManager manager, IRenderer renderer, IWorld world, T entity, float x, float y, int light);

    protected void renderEmotion(IGameInstance game, IAssetManager manager, IRenderer renderer, IWorld world, T entity, float x, float y, int light) {
        Emotion emotion = entity.getEmotion();
        if (emotion != null) {
            float scale = 0.5F;
            float renderX = x - 2F * entity.getWidth() * scale;
            float renderY = y - entity.getHeight();
            manager.getTexture(emotion.getType().texture).draw(renderX, renderY, 2 * scale, 2 * scale, light);
            manager.getTexture(emotion.getTexture()).draw(renderX + 0.5F * scale, renderY + 4/12f * scale, scale, scale, light);
        }
    }

}
