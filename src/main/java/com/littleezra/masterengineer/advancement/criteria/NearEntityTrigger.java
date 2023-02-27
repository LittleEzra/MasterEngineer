package com.littleezra.masterengineer.advancement.criteria;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.littleezra.masterengineer.MasterEngineer;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class NearEntityTrigger extends SimpleCriterionTrigger<NearEntityTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(MasterEngineer.MOD_ID, "near_entity");

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
        return new TriggerInstance(ID, pPlayer, EntityPredicate.Composite.fromJson(pJson, "entity", pContext), GsonHelper.getAsDouble(pJson, "distance"));
    }

    public void trigger(ServerPlayer pPlayer, Entity pEntity) {
        LootContext lootcontext = EntityPredicate.createContext(pPlayer, pEntity);
        this.trigger(pPlayer, (triggerInstance) -> triggerInstance.matches(lootcontext) && pPlayer.distanceToSqr(pEntity) <= triggerInstance.distance);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite entityPredicate;
        private final double distance;

        public TriggerInstance(ResourceLocation pCriterion, EntityPredicate.Composite pPlayer, EntityPredicate.Composite pEntity, double pDistance) {
            super(pCriterion, pPlayer);
            this.entityPredicate = pEntity;
            this.distance = pDistance;
        }

        public boolean matches(LootContext pContext) {
            return this.entityPredicate.matches(pContext) && this.getPlayerPredicate().matches(pContext);
        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.add("entity", this.entityPredicate.toJson(pConditions));
            jsonobject.add("distance", new JsonPrimitive(this.distance));
            return jsonobject;
        }
    }
}
