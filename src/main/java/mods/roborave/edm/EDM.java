package mods.roborave.edm;
import java.util.Arrays;

import mods.roborave.edm.common.CommonProxy;
import mods.roborave.edm.config.Config;
import mods.roborave.edm.helper.LogHandler;
import mods.roborave.edm.helper.VersionChecker;
import mods.roborave.edm.init.Armor;
import mods.roborave.edm.init.Blocks;
import mods.roborave.edm.init.Items;
import mods.roborave.edm.lib.Strings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Strings.MODID, name = Strings.name, version = Strings.version, guiFactory="mods.roborave.edm.gui.ConfigGUIFactory")
//@IMod(id = 1, name = Strings.name)
public class EDM
{
	@SidedProxy
	(
			clientSide="mods.roborave.edm.client.ClientProxy",
			serverSide="mods.roborave.edm.common.CommonProxy"
	)
	
	public static CommonProxy proxy;
	/**
	 * Minecraft's actual version
	 */
	public String acutalMCversion= "1.7.10";
	
	private Blocks blocks;
	
	public static final CreativeTabs tabEDMBlock = new CreativeTabs(CreativeTabs.getNextID(), "EDM:Blocks")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(Blocks.getBlock("Black_diamond_Block"));
        }
    };
	
    public static final CreativeTabs tabEDMItems = new CreativeTabs(CreativeTabs.getNextID(), "EDM:Items")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Items.OD;
        }
    };
	public static final CreativeTabs tabEDMArmor = new CreativeTabs(CreativeTabs.getNextID(), "EDM:Armor")
	{
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Armor.GDH;
		}
	};
	
	public static boolean hardMode;
	
	public static boolean test=true;
	
	public static String descriptionDEV="turn this on for hard mode which will make the swords unenchanted. WIP";
	
	public static Config VersionConfig;
	
	public static Configuration Config;
	
	public LogHandler logger = new LogHandler();
	
	public Strings library;
	
	@Mod.Metadata(Strings.MODID)
	public ModMetadata meta;

	
	@Mod.Instance(Strings.MODID)
	public static EDM Instance;

	public static int ticker;
	public static String category_version=Strings.CONFIG_CATEGORY_VERCHECK;
	
	private static void syncConfig() 
	{
		EDM.getVersionConfig().load();
		EDM.getVersionConfig().save();
	}
	
	@Mod.EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		meta = event.getModMetadata();
		meta.autogenerated = false;
		meta.credits = "(C) Roborave, 2013-2015";
		meta.authorList = Arrays.asList("Roborave");
		meta.modId=Strings.MODID;
		meta.name=Strings.name;
		meta.version=Strings.Vers;
		meta.description="EDM";
		
		
		VersionChecker.registerMod(meta);
		
		EDM.setVersionConfig(new Config(event.getSuggestedConfigurationFile())); 
		syncConfig();
		proxy.PreInit();
    }
	
	
	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals("Extra_Diamonds_Mod"));
           syncConfig();
    }
	

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		if(EDM.getVersionConfig().checkForUpdates()) 
		{
			VersionChecker.startVersionCheck();
		}

		proxy.registerTickers();
		proxy.Init();
	
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		
	}

	
	public Blocks getBlocks() 
	{
		return blocks;
	}
	public void setBlocks(Blocks blocks) 
	{
		this.blocks = blocks;
	}

	public LogHandler getLogger() 
	{
		return logger;
	}

	public void setLogger(LogHandler logger) 
	{
		this.logger = logger;
	}

	public Strings getLibrary() 
	{
		return library;
	}

	public void setLibrary(Strings library) 
	{
		this.library = library;
	}

	public static Config getVersionConfig() {
		return VersionConfig;
	}

	public static void setVersionConfig(Config versionConfig) 
	{
		VersionConfig = versionConfig;
	}
}
