package superbas11.MenuMobs;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import superbas11.MenuMobs.gui.VolumeSliderEntry;

import static net.minecraftforge.common.config.Property.Type.BOOLEAN;
import static net.minecraftforge.common.config.Property.Type.DOUBLE;

public enum ConfigElement {
    SHOW_MAIN_MENU_MOBS("showMainMenuMobs", "superbas11.configgui.showMainMenuMobs",
            "Set to true to show your logged-in player and a random mob on the main menu, false to disable.",
            "true", BOOLEAN),
    SHOW_ONLY_PLAYER_MODELS("showOnlyPlayerModels", "superbas11.configgui.showOnlyPlayerModels", "", "false", BOOLEAN),
    MOB_SOUNDS_VOLUME("mobSoundVolume", "superbas11.configgui.mobSoundVolume", "", "0.5", DOUBLE, 0.0F, 1.0F, VolumeSliderEntry.class),
    ALLOW_DEBUG_OUTPUT("allowDebugOutput", "superbas11.configgui.allowDebugOutput", "", "false", BOOLEAN);

    private String key;
    private String langKey;
    private String desc;
    private Property.Type propertyType;
    private Boolean hasMinMax = false;
    private double min, max;
    private Property Setting;
    private Class<? extends GuiConfigEntries.IConfigEntry> EntryClass;

    ConfigElement(String key, String langKey, String desc, String defaultString, Property.Type propertyType) {
        this.key = key;
        this.langKey = langKey;
        this.desc = desc;
        this.propertyType = propertyType;
        this.Setting = Reference.config.get(Configuration.CATEGORY_GENERAL, key, defaultString, desc, propertyType).setLanguageKey(langKey);
    }

    ConfigElement(String key, String langKey, String desc, String defaultString, Property.Type propertyType, double min, double max, Class<? extends GuiConfigEntries.IConfigEntry> clazz) {
        this(key, langKey, desc, defaultString, propertyType);
        this.min = min;
        this.max = max;
        this.hasMinMax = true;
        this.EntryClass = clazz;
    }

    public String getKey() {
        return key;
    }

    public Property.Type propertyType() {
        return propertyType;
    }

    public Property getSetting() {
        return this.Setting;
    }

    public void syncConfig() {

        this.Setting = Reference.config.get(Configuration.CATEGORY_GENERAL, this.key, this.Setting.getDefault(), this.desc, this.propertyType).setLanguageKey(this.langKey);
        if (this.EntryClass != null)
            this.Setting.setConfigEntryClass(this.EntryClass);
        if (hasMinMax)
            this.Setting.setMinValue(min).setMaxValue(max);
    }
}