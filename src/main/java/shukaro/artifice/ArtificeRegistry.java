package shukaro.artifice;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import shukaro.artifice.util.NameMetaPair;

import java.util.*;

public abstract class ArtificeRegistry
{
    private static final List<Integer> dimensionBlacklist = new ArrayList<Integer>();
    private static final Set<NameMetaPair> stoneTypes = new HashSet<NameMetaPair>();
    private static final List<String> worldTypeBlacklist = new ArrayList<String>();
    private static final Map<NameMetaPair, List<String>> tooltipMap = new HashMap<NameMetaPair, List<String>>();
    private static final List<ItemStack> marbleTypes = new ArrayList<ItemStack>();
    private static final List<ItemStack> basaltTypes = new ArrayList<ItemStack>();

    @SuppressWarnings("unchecked")
    public static void registerMarbleType(ItemStack stack)
    {
        ArtificeCore.logger.info("Registering marble type " + stack.toString());
        if (marbleTypes.isEmpty())
            marbleTypes.add(stack);
        else if (!marbleTypes.contains(stack))
        {
            marbleTypes.add(stack);
            if (!ArtificeConfig.stoneCycleRecipes)
                return;
            if (marbleTypes.size() < 3)
            {
                IRecipe inner = new ShapelessOreRecipe(marbleTypes.get(1), marbleTypes.get(0));
                IRecipe wrap = new ShapelessOreRecipe(marbleTypes.get(0), marbleTypes.get(1));
                CraftingManager.getInstance().getRecipeList().add(inner);
                CraftingManager.getInstance().getRecipeList().add(wrap);
            }
            else
            {
                IRecipe inner = new ShapelessOreRecipe(marbleTypes.get(marbleTypes.size() - 1), marbleTypes.get(marbleTypes.size() - 2));
                IRecipe wrap = new ShapelessOreRecipe(marbleTypes.get(0), marbleTypes.get(marbleTypes.size() - 1));
                for (Object o : CraftingManager.getInstance().getRecipeList().toArray())
                {
                    if (o instanceof ShapelessOreRecipe)
                    {
                        ItemStack output = ((ShapelessOreRecipe) o).getRecipeOutput();
                        if (!output.isItemEqual(marbleTypes.get(0)))
                            continue;
                        ArrayList<Object> input = ((ShapelessOreRecipe) o).getInput();
                        if (input.size() > 1)
                            continue;
                        else if (input.get(0) instanceof ItemStack && ((ItemStack) input.get(0)).isItemEqual(marbleTypes.get(marbleTypes.size() - 2)))
                            CraftingManager.getInstance().getRecipeList().remove(o);
                    }
                }
                CraftingManager.getInstance().getRecipeList().add(inner);
                CraftingManager.getInstance().getRecipeList().add(wrap);
            }
        }
    }

    public static List<ItemStack> getMarbleTypes()
    {
        return marbleTypes;
    }

    @SuppressWarnings("unchecked")
    public static void registerBasaltType(ItemStack stack)
    {
        ArtificeCore.logger.info("Registering basalt type " + stack.toString());
        if (basaltTypes.isEmpty())
            basaltTypes.add(stack);
        else if (!basaltTypes.contains(stack))
        {
            basaltTypes.add(stack);
            if (!ArtificeConfig.stoneCycleRecipes)
                return;
            if (basaltTypes.size() < 3)
            {
                IRecipe inner = new ShapelessOreRecipe(basaltTypes.get(1), basaltTypes.get(0));
                IRecipe wrap = new ShapelessOreRecipe(basaltTypes.get(0), basaltTypes.get(1));
                CraftingManager.getInstance().getRecipeList().add(inner);
                CraftingManager.getInstance().getRecipeList().add(wrap);
            }
            else
            {
                IRecipe inner = new ShapelessOreRecipe(basaltTypes.get(basaltTypes.size() - 1), basaltTypes.get(basaltTypes.size() - 2));
                IRecipe wrap = new ShapelessOreRecipe(basaltTypes.get(0), basaltTypes.get(basaltTypes.size() - 1));
                for (Object o : CraftingManager.getInstance().getRecipeList().toArray())
                {
                    if (o instanceof ShapelessOreRecipe)
                    {
                        ItemStack output = ((ShapelessOreRecipe) o).getRecipeOutput();
                        if (!output.isItemEqual(basaltTypes.get(0)))
                            continue;
                        ArrayList<Object> input = ((ShapelessOreRecipe) o).getInput();
                        if (input.size() > 1)
                            continue;
                        else if ((input.get(0) instanceof ItemStack) && ((ItemStack) input.get(0)).isItemEqual(basaltTypes.get(basaltTypes.size() - 2)))
                            CraftingManager.getInstance().getRecipeList().remove(o);
                    }
                }
                CraftingManager.getInstance().getRecipeList().add(inner);
                CraftingManager.getInstance().getRecipeList().add(wrap);
            }
        }
    }

    public static List<ItemStack> getBasaltTypes()
    {
        return basaltTypes;
    }

    public static void registerTooltip(ItemStack stack, String line)
    {
        NameMetaPair pair = new NameMetaPair(stack.getItem(), stack.getItemDamage());
        if (tooltipMap.get(pair) == null)
        {
            List<String> temp = new ArrayList<String>();
            temp.add(line);
            tooltipMap.put(pair, temp);
        }
        else
        {
            tooltipMap.get(pair).add(line);
        }
    }

    public static Map<NameMetaPair, List<String>> getTooltipMap()
    {
        return tooltipMap;
    }

    public static void registerDimensionBlacklist(int dimID)
    {
        if (!dimensionBlacklist.contains(dimID))
        {
            ArtificeCore.logger.info("Registering " + dimID + " to dimension blacklist");
            dimensionBlacklist.add(dimID);
        }
    }

    public static List<Integer> getDimensionBlacklist()
    {
        return dimensionBlacklist;
    }

    public static void registerStoneType(Block stone, int meta)
    {
        NameMetaPair pair = new NameMetaPair(stone, meta);
        if (!stoneTypes.contains(pair))
        {
            ArtificeCore.logger.info("Registering stone type " + pair.toString());
            stoneTypes.add(pair);
        }
    }

    public static Set<NameMetaPair> getStoneTypes()
    {
        return stoneTypes;
    }

    public static void registerWorldTypeBlacklist(String type)
    {
        if (!worldTypeBlacklist.contains(type))
        {
            ArtificeCore.logger.info("Registering " + type + " to world type blacklist");
            worldTypeBlacklist.add(type);
        }
    }

    public static List<String> getWorldTypeBlacklist()
    {
        return worldTypeBlacklist;
    }
}
