package com.littleezra.masterengineer.screen;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.client.ClientPassageData;
import com.littleezra.masterengineer.networking.ModMessages;
import com.littleezra.masterengineer.networking.packets.UsePassageC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;

public class PassageScreen extends AbstractContainerScreen<PassageMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MasterEngineer.MOD_ID, "textures/gui/passage.png");

    private final List<AbstractButton> passageButtons = new ArrayList<>();
    private final List<PassageDestinationButton> destinationButtons = new ArrayList<>();
    private int scrollIndex;

    private List<BlockPos> passages;

    public PassageScreen(PassageMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    private <T extends AbstractButton> void addPassageButton(T pBeaconButton) {
        this.addRenderableWidget(pBeaconButton);
        this.passageButtons.add(pBeaconButton);
    }
    private void addDestinationButton(PassageDestinationButton pButton){
        this.addWidget(pButton);
        this.destinationButtons.add(pButton);
    }

    @Override
    protected void init() {
        super.init();
        this.passageButtons.clear();
        addPassageButton(new PassageScrollUpButton(this.leftPos + 42, this.topPos + 13));
        addPassageButton(new PassageScrollDownButton(this.leftPos + 42, this.topPos + 60));

        MasterEngineer.printDebug(this.passages.size());

        this.passages = ClientPassageData.getPassages();
        this.destinationButtons.clear();
        for (BlockPos pos : this.passages){
            this.destinationButtons.add(new PassageDestinationButton(pos));
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if (this.passages != null){
            if (scrollIndex < this.passages.size()){
                this.destinationButtons.get(scrollIndex).setPos(x + 60, y + 14);
            }
            if (scrollIndex + 1 < this.passages.size()){
                this.destinationButtons.get(scrollIndex + 1).setPos(x + 60, y + 33);
            }
            if (scrollIndex + 2 < this.passages.size()){
                this.destinationButtons.get(scrollIndex + 2).setPos(x + 60, y + 52);
            }
        }
    }

    private void renderListItem(PoseStack pPoseStack, int x, int y, int index, BlockPos pos){
        int xPos = x + 60;
        int yPos = y + 14 + index * 19;
        blit(pPoseStack, xPos, yPos, 0, 166,108, 19);
        this.font.draw(pPoseStack, MasterEngineer.getBlockPosString(pos), xPos + 5, yPos + 5, 4210752);
    }
    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    public void scrollDown(){
        if (scrollIndex < this.passages.size() - 3){
            passageButtons.get(0).active = true;
            scrollIndex++;
        }
        else {
            passageButtons.get(1).active = false;
        }
    }
    public void scrollUp(){
        if (scrollIndex > 0){
            passageButtons.get(1).active = true;
            scrollIndex--;
        }
        else{
            passageButtons.get(0).active = false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    class PassageScrollUpButton extends AbstractButton {
        protected PassageScrollUpButton(int pX, int pY) {
            super(pX, pY, 17, 12, CommonComponents.EMPTY);
        }

        protected PassageScrollUpButton(int pX, int pY, Component pMessage) {
            super(pX, pY, 17, 12, pMessage);
        }

        public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, PassageScreen.TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = 219;
            int vOffset = 166;
            if (!this.active) {
                vOffset += this.height * 2;
            } else if (this.isHoveredOrFocused()) {
                vOffset += this.height;
            }

            this.blit(pPoseStack, this.getX(), this.getY(), 108, vOffset, this.width, this.height);
        }

        public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
            this.defaultButtonNarrationText(pNarrationElementOutput);
        }

        @Override
        public void onPress() {
            PassageScreen.this.scrollUp();
        }
    }
    @OnlyIn(Dist.CLIENT)
    class PassageScrollDownButton extends AbstractButton {
        protected PassageScrollDownButton(int pX, int pY) {
            super(pX, pY, 17, 12, CommonComponents.EMPTY);
        }

        protected PassageScrollDownButton(int pX, int pY, Component pMessage) {
            super(pX, pY, 17, 12, pMessage);
        }

        public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, PassageScreen.TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = 219;
            int vOffset = 166;
            if (!this.active) {
                vOffset += this.height * 2;
            } else if (this.isHoveredOrFocused()) {
                vOffset += this.height;
            }

            this.blit(pPoseStack, this.getX(), this.getY(), 125, vOffset, this.width, this.height);
        }

        public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
            this.defaultButtonNarrationText(pNarrationElementOutput);
        }

        @Override
        public void onPress() {
            PassageScreen.this.scrollDown();
        }
    }

    class PassageDestinationButton extends AbstractButton{
        private final BlockPos pos;

        protected PassageDestinationButton(BlockPos pos) {
            super(0, 0, 17, 12, CommonComponents.EMPTY);
            this.pos = pos;
        }

        protected PassageDestinationButton(BlockPos pos, Component pMessage) {
            super(0, 0, 108, 19, pMessage);
            this.pos = pos;
        }

        public void setPos(int pX, int pY){
            this.setX(pX);
            this.setY(pY);
        }

        public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, PassageScreen.TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = 219;
            int vOffset = 166;
            if (!this.active) {
                vOffset += this.height * 2;
            } else if (this.isHoveredOrFocused()) {
                vOffset += this.height;
            }

            this.blit(pPoseStack, this.getX(), this.getY(), 125, vOffset, this.width, this.height);
            font.draw(pPoseStack, MasterEngineer.getBlockPosString(pos), this.getX() + 5, this.getY() + 5, 4210752);
        }

        public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
            this.defaultButtonNarrationText(pNarrationElementOutput);
        }

        @Override
        public void onPress() {
            ModMessages.sendToServer(new UsePassageC2SPacket(pos));
        }
    }
}
