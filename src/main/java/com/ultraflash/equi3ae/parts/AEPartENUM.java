package com.ultraflash.equi3ae.parts;

import appeng.api.config.Upgrades;
import com.ultraflash.equi3ae.item.ItemEnum;
import com.ultraflash.equi3ae.reference.StringE3AE;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

import static com.ultraflash.equi3ae.item.ItemEnum.VALUES;

/**
 * Created by Benni on 28.06.2016.
 */


    public enum AEPartEnum
    {

     // EssentiaTerminal (StringE3AE.Part_EssentiaTerminal, PartEssentiaTerminal.class),
       // EssentiaStorageMonitor (StringE3AE.Part_EssentiaStorageMonitor, PartEssentiaStorageMonitor.class);

;
        /**
         * Cached enum values
         */
        public static final AEPartEnum[] VALUES = AEPartEnum.values();

        private StringE3AE unlocalizedName;

        private Class<? extends PartBaseE3AE> partClass;

        private String groupName;

        private Map<Upgrades, Integer> upgrades = new HashMap<Upgrades, Integer>();

        private AEPartEnum( final StringE3AE unlocalizedName, final Class<? extends PartBaseE3AE> partClass )
        {
            this( unlocalizedName, partClass, null );
        }

        private AEPartEnum( final StringE3AE unlocalizedName, final Class<? extends PartBaseE3AE> partClass, final String groupName )
        {
            // Set the localization string
            this.unlocalizedName = unlocalizedName;

            // Set the class
            this.partClass = partClass;

            // Set the group name
            this.groupName = groupName;
        }

        private AEPartEnum(	final StringE3AE unlocalizedName, final Class<? extends PartBaseE3AE> partClass, final String groupName,
                                final Pair<Upgrades, Integer>... upgrades )
        {
            this( unlocalizedName, partClass, groupName );

            for( Pair<Upgrades, Integer> pair : upgrades )
            {
                // Add the upgrade to the map
                this.upgrades.put( pair.getKey(), pair.getValue() );
            }

        }

        private static Pair<Upgrades, Integer> generatePair( final Upgrades upgrade, final int maximum )
        {
            return new ImmutablePair<Upgrades, Integer>( upgrade, Integer.valueOf( maximum ) );
        }


        public static AEPartEnum getPartFromDamageValue( final ItemStack itemStack )
        {
            // Clamp the damage
            int clamped = MathHelper.clamp_int( itemStack.getItemDamage(), 0, AEPartEnum.VALUES.length - 1 );

            // Get the part
            return AEPartEnum.VALUES[clamped];
        }

        public static int getPartID( final Class<? extends PartBaseE3AE> partClass )
        {
            int id = -1;

            // Check each part
            for( int i = 0; i < AEPartEnum.VALUES.length; i++ )
            {
                // Is it the same as the specified part?
                if( AEPartEnum.VALUES[i].getPartClass().equals( partClass ) )
                {
                    // Found the id, set and stop searching
                    id = i;
                    break;
                }
            }

            // Return the id
            return id;

        }

        public PartBaseE3AE createPartInstance( final ItemStack itemStack ) throws InstantiationException, IllegalAccessException
        {
            // Create a new instance of the part
            PartBaseE3AE part = this.partClass.newInstance();

            // Setup based on the itemStack
            part.setupPartFromItem( itemStack );

            // Return the newly created part
            return part;

        }

        /**
         * Gets the group associated with this part.
         *
         * @return
         */
        public String getGroupName()
        {
            return this.groupName;
        }

        public String getLocalizedName()
        {
            return this.unlocalizedName.getLocalized();
        }

        /**
         * Gets the class associated with this part.
         *
         * @return
         */
        public Class<? extends PartBaseE3AE> getPartClass()
        {
            return this.partClass;
        }

        public ItemStack getStack()
        {
            return ItemEnum.ITEM_AEPART.getDMGStack( this.ordinal() );
        }

        /**
         * Gets the unlocalized name for this part.
         *
         * @return
         */
        public String getUnlocalizedName()
        {
            return this.unlocalizedName.getUnlocalized();
        }

        public Map<Upgrades, Integer> getUpgrades()
        {
            return this.upgrades;
        }
    }






