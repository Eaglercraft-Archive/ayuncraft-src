package net.minecraft.src;

import java.util.Collection;
import java.util.Iterator;

import me.ayunami2000.ayuncraft.tmi.TMIConfig;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

public abstract class InventoryEffectRenderer extends GuiContainer {
	private boolean field_74222_o;

	public InventoryEffectRenderer(Container par1Container) {
		super(par1Container);
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		super.initGui();

		if (!this.mc.thePlayer.getActivePotionEffects().isEmpty()) {
			if(!TMIConfig.getInstance().isEnabled())this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
			this.field_74222_o = true;
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);

		if (this.field_74222_o) {
			this.displayDebuffEffects();
		}
	}
	
	private static final TextureLocation tex_inventory = new TextureLocation("/gui/inventory.png");

	/**
	 * Displays debuff/potion effects that are currently being applied to the player
	 */
	private void displayDebuffEffects() {
		int var1 = this.guiLeft - 124;
		int var2 = this.guiTop;
		Collection var4 = this.mc.thePlayer.getActivePotionEffects();

		if (!var4.isEmpty()) {
			EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			EaglerAdapter.glDisable(EaglerAdapter.GL_LIGHTING);
			int var5 = 33;

			if (var4.size() > 5) {
				var5 = 132 / (var4.size() - 1);
			}

			for (Iterator var6 = this.mc.thePlayer.getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5) {
				PotionEffect var7 = (PotionEffect) var6.next();
				Potion var8 = Potion.potionTypes[var7.getPotionID()];
				EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				tex_inventory.bindTexture();
				this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

				if (var8.hasStatusIcon()) {
					int var9 = var8.getStatusIconIndex();
					this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
				}

				String var11 = StatCollector.translateToLocal(var8.getName());

				if (var7.getAmplifier() == 1) {
					var11 = var11 + " II";
				} else if (var7.getAmplifier() == 2) {
					var11 = var11 + " III";
				} else if (var7.getAmplifier() == 3) {
					var11 = var11 + " IV";
				}

				this.fontRenderer.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
				String var10 = Potion.getDurationString(var7);
				this.fontRenderer.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
			}
		}
	}
}
