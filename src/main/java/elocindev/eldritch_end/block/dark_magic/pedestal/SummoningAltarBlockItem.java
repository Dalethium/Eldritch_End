package elocindev.eldritch_end.block.dark_magic.pedestal;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import elocindev.necronomicon.api.text.TextAPI;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SummoningAltarBlockItem extends BlockItem {

    public SummoningAltarBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        MutableText type = TextAPI.Styles.getGradient(Text.translatable("block.eldritch_end.eldritch_pedestal.type"), 1, 0x5c3885, 0xe88c13, 1.0F);

        tooltip.add(type.fillStyle(type.getStyle().withUnderline(true)));
    }
}
