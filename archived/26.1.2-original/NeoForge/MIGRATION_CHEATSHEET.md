# Useful Tools Mod — NeoForge 1.21.1 (21.1.219) → 26.1 Migration Cheatsheet

This is a focused, deduplicated cheatsheet of every API change between NeoForge 1.21.1 and 26.1
that can plausibly affect this mod's surface areas. Group is by topic, not by version. Each entry
lists: **what changed** (old → new), **primer version** that introduced the change, and **what
to do** (the concrete transformation).

Primer versions referenced: 1.21.2, 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, 1.21.10,
1.21.11, 26.1. (1.21.3 was an emergency vanilla bugfix and has no separate primer; its breaking
changes are folded into 1.21.4.)

---

## 1. Registration: DeferredRegister, Properties, ResourceKeys

### 1.1 Item.Properties / BlockBehaviour.Properties require `.setId(ResourceKey)`
**Primer:** 1.21.2
**Old:**
```java
ITEMS.register("foo", () -> new Item(new Item.Properties()));
BLOCKS.register("bar", () -> new Block(BlockBehaviour.Properties.of().strength(2.0f)));
```
**New:** Every Item.Properties and Block.Properties must have its id set before being passed to
the constructor. `DeferredRegister.Items#registerItem(...)` / `registerBlock(...)` helpers do
this for you — use them.
```java
ITEMS.registerItem("foo", props -> new Item(props));         // helper sets .setId for you
BLOCKS.registerBlock("bar", props -> new Block(props), BlockBehaviour.Properties.of().strength(2.0f));
```
If hand-rolling: `props.setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "foo")))`.

### 1.2 `new ResourceLocation(ns, path)` is removed
**Primer:** 1.21.2 (deprecated earlier, gone now)
**Old:** `new ResourceLocation("modid", "foo")`
**New:** `ResourceLocation.fromNamespaceAndPath("modid", "foo")` or `ResourceLocation.parse("modid:foo")` or `ResourceLocation.withDefaultNamespace("foo")`.

### 1.3 `ResourceLocation` → `Identifier` rename
**Primer:** 1.21.11
**Old:** `net.minecraft.resources.ResourceLocation`, `net.minecraft.ResourceLocationException`
**New:** `net.minecraft.util.Identifier`, `net.minecraft.util.IdentifierException`
**What to do:** Project-wide search/replace imports. `FriendlyByteBuf#readResourceLocation` /
`writeResourceLocation` → `readIdentifier` / `writeIdentifier`. DeferredRegister itself does
not change shape.

### 1.4 NeoForge `RegistryObject` → `DeferredHolder` / `Holder<T>`
**Primer:** Already in 1.21.x line; reinforced 26.1
**Old:** `public static final RegistryObject<Item> FOO = ITEMS.register(...)`
**New:** `public static final DeferredHolder<Item, Item> FOO = ITEMS.register(...)`
Or use `DeferredRegister.Items.registerItem(...)` which returns `DeferredItem<Item>`.
`.get()` still works; `.value()` is the Holder-style accessor.

### 1.5 Utility package reorganization
**Primer:** 1.21.11
**Old:** `net.minecraft.Util`, `net.minecraft.BlockUtil`, `net.minecraft.FileUtil` (root package)
**New:** `net.minecraft.util.Util`, `net.minecraft.util.BlockUtil`, `net.minecraft.util.FileUtil`
**What to do:** Update imports.

---

## 2. Tools: SwordItem, PickaxeItem, ShovelItem, AxeItem, HoeItem

### 2.1 Custom `Tier` interface → `ToolMaterial` record
**Primer:** 1.21.2
**Old:**
```java
public class MyTier implements Tier {
    public int getUses()          { return 250; }
    public float getSpeed()       { return 6.0f; }
    public float getAttackDamageBonus() { return 2.0f; }
    public int getLevel()         { return 2; }
    public int getEnchantmentValue() { return 14; }
    public Ingredient getRepairIngredient() { ... }
}
new PickaxeItem(MY_TIER, 1, -2.8f, new Item.Properties());
```
**New (1.21.2):**
```java
public static final ToolMaterial MY_MATERIAL = new ToolMaterial(
    BlockTags.INCORRECT_FOR_IRON_TOOL,   // blocks it CANNOT mine
    250,                                  // durability
    6.0f,                                 // speed
    2.0f,                                 // attack bonus
    14,                                   // enchantment value
    ItemTags.IRON_TOOL_MATERIALS          // repair tag
);
new PickaxeItem(MY_MATERIAL, 1.0f, -2.8f, new Item.Properties().setId(...));
```
Note: incorrect-for tag, not correct-for; and damage/speed are now floats on the tool itself.

### 2.2 Sword/Pickaxe/Shovel/Axe/HoeItem **classes removed**
**Primer:** 1.21.5
**Old:** `new SwordItem(tier, attackDmg, attackSpeed, props)`
**New:** Use plain `Item` and add components, OR use the Item.Properties convenience methods:
```java
new Item(new Item.Properties()
    .sword(MY_MATERIAL, 3.0f, -2.4f))      // configures TOOL + ATTRIBUTE_MODIFIERS + WEAPON
new Item(new Item.Properties().pickaxe(MY_MATERIAL, 1.0f, -2.8f))
new Item(new Item.Properties().axe(MY_MATERIAL, 6.0f, -3.1f))
new Item(new Item.Properties().shovel(MY_MATERIAL, 1.5f, -3.0f))
new Item(new Item.Properties().hoe(MY_MATERIAL, 0f, -3.0f))
```
**What to do for custom subclass behavior:** Your custom `MySword extends SwordItem` classes
need to become `extends Item` (or composed via components). If you used `releaseUsing`,
`useOn`, `getDestroySpeed`, etc., override those on a plain `Item` subclass; the destroy speed
now comes from the `DataComponents.TOOL` component which `.sword()/.pickaxe()` etc. set.

### 2.3 `Tier#applyToolProperties` gained a parameter
**Primer:** 1.21.5 (only relevant if you still hand-implement Tier)
**Old:** `void applyToolProperties(Item.Properties)`
**New:** `void applyToolProperties(Item.Properties, boolean canDisableShield)`

### 2.4 `Item.Properties#enchantable(int)` replaces `Tier#getEnchantmentValue()` on the item
**Primer:** 1.21.2
**Old:** Read from Tier.
**New:** `new Item(new Item.Properties().enchantable(14))` writes the `ENCHANTABLE` component.

---

## 3. Armor: ArmorMaterials, ArmorItem

### 3.1 `ArmorItem` class **removed**; armor materials reworked
**Primer:** 1.21.2 (initial restructure) → 1.21.4 (asset key) → 1.21.5 (subclass removal)
**Old (1.21.1):**
```java
new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.HELMET, new Item.Properties())
```
**New (1.21.5+):**
```java
public static final ArmorMaterial MY_ARMOR_MATERIAL = new ArmorMaterial(
    15,                                                   // durability scalar
    Util.make(new EnumMap<>(ArmorType.class), m -> {
        m.put(ArmorType.HELMET,     2);
        m.put(ArmorType.CHESTPLATE, 6);
        m.put(ArmorType.LEGGINGS,   5);
        m.put(ArmorType.BOOTS,      2);
        m.put(ArmorType.BODY,       4);                   // wolf armor
    }),
    25,                                                   // enchantment value
    SoundEvents.ARMOR_EQUIP_IRON,
    0.0f,                                                 // toughness
    0.0f,                                                 // knockback resistance
    ItemTags.REPAIRS_IRON_ARMOR,                          // repair tag
    ResourceKey.create(EquipmentAssets.ROOT_ID,           // asset key (1.21.4+)
        ResourceLocation.fromNamespaceAndPath(MODID, "my_armor"))
);

ITEMS.registerItem("my_helmet", props -> new Item(
    props.humanoidArmor(MY_ARMOR_MATERIAL, ArmorType.HELMET)));
```
The `.humanoidArmor(material, ArmorType)` convenience sets `EQUIPPABLE`, `ARMOR`,
`ATTRIBUTE_MODIFIERS`, durability, etc. Variants: `.wolfArmor()`, `.horseArmor()`.

### 3.2 `ArmorItem.Type` → `ArmorType`
**Primer:** 1.21.2
**What to do:** Rename usages: `ArmorItem.Type.HELMET` → `ArmorType.HELMET`.

### 3.3 Equipment model now lives in `assets/<modid>/equipment/<name>.json`
**Primer:** 1.21.2 (concept) / 1.21.4 (ResourceKey<EquipmentAsset>)
Provide a JSON file referenced by the asset key declared in the ArmorMaterial:
```json
{
  "layers": {
    "humanoid":      [{ "texture": "modid:my_armor" }],
    "humanoid_leggings": [{ "texture": "modid:my_armor" }]
  }
}
```
The texture files now live in `assets/modid/textures/entity/equipment/humanoid/my_armor.png`
(and `.../humanoid_leggings/my_armor.png` for the leggings layer). Old
`textures/models/armor/foo_layer_1.png` paths no longer work.

### 3.4 Custom armor render-layer code path
**Primer:** 1.21.2
**Old:** `HumanoidArmorLayer` reads `ArmorItem#getMaterial()`.
**New:** It reads the `DataComponents.EQUIPPABLE` component's asset id and looks up the JSON.
If you have a custom render layer, use `EquipmentLayerRenderer#renderLayers(...)`.

---

## 4. Blocks & BlockEntities (SpectralInfuser etc.)

### 4.1 Block / BlockBehaviour Properties now require `.setId(...)`
**Primer:** 1.21.2  See §1.1.

### 4.2 `BlockEntityType.Builder.of(...)` → `BlockEntityType.Builder.of(...).build(null)` still valid
**Primer:** 1.21.2
Block-entity registration is otherwise unchanged. The `BlockEntityType` constructor that takes
`MyBlockEntity::new` (BiFunction) is still available via `BlockEntityType.Builder.of(MyBE::new, MyBlocks.SPECTRAL_INFUSER.get())`.

### 4.3 `Block#getCloneItemStack` signature
**Primer:** 1.21.4
**Old:** `getCloneItemStack(BlockState state)`
**New:** `getCloneItemStack(BlockState state, boolean includeData, boolean copyBlockData)`
If you override it on `SpectralInfuserBlock`, update the signature.

### 4.4 Furnace-like field name changes (AbstractFurnaceBlockEntity)
**Primer:** 1.21.4
| Old | New |
|---|---|
| `litTime` | `litTimeRemaining` |
| `litDuration` | `litTotalTime` |
| `cookingProgress` | `cookingTimer` |
**What to do:** If your `SpectralInfuserBlockEntity` extends or mirrors the furnace fields,
rename the fields and any references (data slots in the menu, screen progress arrows).

### 4.5 `BlockEntity#onRemove` split
**Primer:** 1.21.5
**Old:**
```java
public void onRemove(BlockState state, Level lvl, BlockPos pos, BlockState newState, boolean moved) {
    Containers.dropContentsOnDestroy(state, lvl, pos, this);
    super.onRemove(...);
}
```
**New:** Override in your **Block** subclass, not the BE. Two new hooks:
```java
@Override
protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel lvl, BlockPos pos, boolean moved) {
    Containers.updateNeighboursAfterDestroy(state, pos, lvl);
}
// For dropping container contents: implement Container on the BE, drops happen automatically
// via BlockBehaviour#onRemove default. If you need custom drop logic:
@Override
protected void onRemove(BlockState state, Level lvl, BlockPos pos, BlockState newState, boolean moved) {
    // optional pre-removal side effects
}
```

### 4.6 `BlockBehaviour#entityInside` gained a parameter
**Primer:** 1.21.10
**Old:** `void entityInside(BlockState, Level, BlockPos, Entity)`
**New:** `void entityInside(BlockState, Level, BlockPos, Entity, InsideBlockEffectApplier)` /
or with `boolean intersecting` overload (NeoForge surfaces this).
**What to do:** Update override signature.

### 4.7 VoxelShape helpers
**Primer:** 1.21.5
**Old:** Map-based cache pattern with `EnumMap<Direction, VoxelShape>`.
**New:** `Block.getShapeForEachState(Function<BlockState, VoxelShape>)` returns a cached
`Function<BlockState, VoxelShape>`. Plus `Block.boxes`, `Block.cube`, `Block.column`,
`Shapes.rotateHorizontal`, `Shapes.rotateAll`, `Shapes.blockOccludes` (note spelling fix from
`blockOccudes`).
Use this if the SpectralInfuser has a multi-state collision shape; otherwise no change.

### 4.8 `Block#use` → `Block#useItemOn` (and `InteractionResult` overhaul)
**Primer:** 1.21.2
**Old:**
```java
public InteractionResult use(BlockState s, Level l, BlockPos p, Player pl, InteractionHand h, BlockHitResult hit)
```
**New:** Two methods, separated by whether an item is involved:
```java
protected ItemInteractionResult useItemOn(ItemStack stack, BlockState s, Level l, BlockPos p, Player pl, InteractionHand h, BlockHitResult hit)
protected InteractionResult useWithoutItem(BlockState s, Level l, BlockPos p, Player pl, BlockHitResult hit)
```
Return types: `ItemInteractionResult.SUCCESS`, `CONSUME`, `PASS_TO_DEFAULT_BLOCK_INTERACTION`,
`FAIL`, or `SKIP_DEFAULT_BLOCK_INTERACTION`. For opening a menu (SpectralInfuser):
```java
@Override
protected InteractionResult useWithoutItem(BlockState state, Level lvl, BlockPos pos, Player p, BlockHitResult hit) {
    if (!lvl.isClientSide) p.openMenu(state.getMenuProvider(lvl, pos));
    return InteractionResult.sidedSuccess(lvl.isClientSide);
}
```

### 4.9 `InteractionResult` itself simplified (1.21.2)
**Primer:** 1.21.2
Constants are now `SUCCESS`, `SUCCESS_SERVER`, `CONSUME`, `FAIL`, `PASS`, `TRY_WITH_EMPTY_HAND`.
`InteractionResultHolder<ItemStack>` is gone in many places — `Item#use` now returns
`InteractionResult`, and item transformation uses `InteractionResult#heldItemTransformedTo(stack)`.

---

## 5. Menus & AbstractContainerScreen (SpectralInfuser GUI)

### 5.1 `AbstractContainerScreen#render` invokes new methods in 1.21.6
**Primer:** 1.21.6
**Old contract:**
```java
public void render(GuiGraphics gg, int mx, int my, float pt) {
    this.renderBackground(gg, mx, my, pt);
    super.render(gg, mx, my, pt);     // calls renderBg, renderLabels
    this.renderTooltip(gg, mx, my);
}
protected void renderBg(GuiGraphics gg, float pt, int mx, int my) { ... }
protected void renderLabels(GuiGraphics gg, int mx, int my) { ... }
```
**New (1.21.6+):**
- `renderBg` still exists and is still called for the background image (signature unchanged:
  `renderBg(GuiGraphics, float partialTick, int mouseX, int mouseY)`).
- New: `renderContents(GuiGraphics, int, int, float)` is the unified slot/labels stage.
  `renderLabels` is still invoked from inside it for back-compat but consider migrating.
- The two-phase render means **draw call ordering can change**. Things you blit in
  `renderBg` may appear behind/above items differently than before. Test the progress arrow,
  fire icon, and any custom overlay.

### 5.2 Tooltips: `setTooltipForNextRenderPass` → `setTooltipForNextFrame`
**Primer:** 1.21.6
**Old:** `Screen#setTooltipForNextRenderPass(Component)`
**New:** `GuiGraphics#setTooltipForNextFrame(Component)` /
`setComponentTooltipForNextFrame(List<Component>)`.

### 5.3 GuiGraphics rendering pipeline rewrite
**Primer:** 1.21.6
- `GuiGraphics#flush()` removed (auto via GuiRenderState).
- `GuiGraphics#pose()` returns `Matrix3x2fStack` (was `PoseStack` wrapping `Matrix4f`).
  2D math only — `pushPose/popPose` still exist, but you no longer mulPoseMatrix Matrix4f.
- `drawString` colors must include alpha in the top 8 bits. `0xFFFFFF` (no alpha) is now
  invisible. Use `0xFFFFFFFF` or `ARGB.opaque(0xFFFFFF)`.
- `GuiGraphics#blit` — use the new sampler-aware form (see §5.4).

### 5.4 `GuiGraphics#blit` signature
**Primer:** 1.21.2 (initial RenderType param), 1.21.4 (settles), 1.21.11 (sampler-aware)
**Old (1.21.1):** `gg.blit(TEXTURE, x, y, u, v, w, h);`
**New (1.21.2+):**
```java
gg.blit(RenderType::guiTextured, TEXTURE, x, y, u, v, w, h, 256, 256);
```
The trailing `256, 256` are the texture PNG dimensions. The first arg is a `Function<ResourceLocation, RenderType>` (use `RenderType::guiTextured` or `RenderType::guiTexturedOverlay`).

### 5.5 `RenderType` → `RenderTypes` static accessors (some renames)
**Primer:** 1.21.11
For preset render types the static-accessor class is now `RenderTypes` (e.g. `RenderTypes.solid()`),
while custom render types now build off `RenderSetup` / `RenderPipelines`. If you only ever pass
`RenderType::guiTextured` as a method reference, you may need to switch to `RenderTypes::guiTextured`.

### 5.6 `renderTooltip` deferred
**Primer:** 1.21.6
**Old:** `renderTooltip(gg, mx, my)` from inside `render`.
**New:** `gg.setTooltipForNextFrame(...)` + automatic deferred render. Migrate calls.

### 5.7 1.21.9 rendering pipeline – `submit*` API
**Primer:** 1.21.9
**Note:** Most affects entity/block-entity renderers (see §7). For a vanilla-like menu screen
extending `AbstractContainerScreen`, **you generally don't need to migrate to submit-style
calls** — `GuiGraphics` continues to expose blit/drawString/fill. But:
- `GuiGraphics#renderOutline` → `submitOutline`
- `GuiGraphics#renderDeferredTooltip` → `renderDeferredElements`
Only relevant if you call these directly.

---

## 6. Entities (GhostEntity, GrenadeEntity), AI Goals

### 6.1 EntityType registration unchanged
**Primer:** 1.21.x – 26.1 (no change)
`DeferredRegister<EntityType<?>>` and `EntityType.Builder.of(...).build(id)` work identically.
The `id` arg in `.build("ghost")` is just an internal string used by DataFixerUpper; not a
ResourceKey change.

### 6.2 `Mob#getArmorSlots`, individual slot access → unified `EntityEquipment`
**Primer:** 1.21.5
**Old:**
```java
entity.setItemSlot(EquipmentSlot.HEAD, stack);
ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
Iterable<ItemStack> armor = entity.getArmorSlots();
```
**New:**
```java
entity.equipment.set(EquipmentSlot.HEAD, stack);
ItemStack head = entity.equipment.get(EquipmentSlot.HEAD);
// getArmorSlots is gone; iterate EquipmentSlot.VALUES or use entity.equipment.iterator()
```
For Ghost/Grenade (non-armored), only relevant if you do `setItemSlot` for held weapons.

### 6.3 New `EquipmentSlot.SADDLE`
**Primer:** 1.21.5
Only matters if your entity is `Saddleable`.

### 6.4 Entity NBT: `CompoundTag` → `ValueInput`/`ValueOutput`
**Primer:** 1.21.6
**Old:**
```java
@Override
protected void addAdditionalSaveData(CompoundTag tag) {
    tag.putInt("Fuse", this.fuse);
}
@Override
protected void readAdditionalSaveData(CompoundTag tag) {
    this.fuse = tag.getInt("Fuse");
}
```
**New:**
```java
@Override
protected void addAdditionalSaveData(ValueOutput out) {
    out.putInt("Fuse", this.fuse);
}
@Override
protected void readAdditionalSaveData(ValueInput in) {
    this.fuse = in.getIntOr("Fuse", 0);    // NOTE: returns int directly, no Optional
}
```
For lists: `out.list("Items", MyCodec)`, `in.list("Items", MyCodec)`. Same migration applies to
**BlockEntity** `loadAdditional` / `saveAdditional`.

Also note: `Entity.UUID_TAG` → `Entity.TAG_UUID`, `LivingEntity.ATTRIBUTES_FIELD` →
`LivingEntity.TAG_ATTRIBUTES`.

### 6.5 `ContainerHelper.saveAllItems` / `loadAllItems` signature change
**Primer:** 1.21.6
**Old:** `saveAllItems(CompoundTag, NonNullList<ItemStack>, HolderLookup.Provider)` — needed for
data components.
**New:** `saveAllItems(ValueOutput, NonNullList<ItemStack>)` — no HolderLookup; components
serialize through ValueOutput.
**What to do:** For SpectralInfuserBlockEntity inventory persistence:
```java
@Override
protected void saveAdditional(ValueOutput out) {
    super.saveAdditional(out);
    ContainerHelper.saveAllItems(out, this.items);
}
@Override
protected void loadAdditional(ValueInput in) {
    super.loadAdditional(in);
    ContainerHelper.loadAllItems(in, this.items);
}
```

### 6.6 `LivingEntity#isLookingAtMe` and `hasLineOfSight` simplified
**Primer:** 1.21.4
- `isLookingAtMe(LivingEntity, Predicate<LivingEntity>, DoubleSupplier[])` →
  `isLookingAtMe(LivingEntity, double[])`.
- `hasLineOfSight(Entity, DoubleSupplier)` → `hasLineOfSight(Entity, double)`.

### 6.7 AI Goals — no API changes
`Goal`, `GoalSelector`, `TargetSelector`, `PathfinderMob` etc. unchanged through 26.1.

### 6.8 `Player#canInteractWithEntity` → `isWithinEntityInteractionRange`
**Primer:** 1.21.11
**Old:** `player.canInteractWithEntity(entity)`
**New:** `player.isWithinEntityInteractionRange(entity)`. Also new
`canInteractWithBlock(pos, padding)`.

### 6.9 `Item#inventoryTick` signature
**Primer:** 1.21.5
**Old:** `inventoryTick(ItemStack, Level, Entity, int slotId, boolean isSelected)`
**New:** `inventoryTick(ItemStack, ServerLevel, Entity, EquipmentSlot)` — server-only,
EquipmentSlot instead of int. If you've overridden this on the grenade or any item that
ticks, update the signature.

---

## 7. Entity Renderers & Models (major rewrites)

### 7.1 EntityRenderer is now generic over a RenderState (1.21.2)
**Primer:** 1.21.2
**Old:**
```java
public class GhostRenderer extends MobRenderer<GhostEntity, GhostModel<GhostEntity>> {
    public ResourceLocation getTextureLocation(GhostEntity e) { ... }
}
public class GhostModel<T extends GhostEntity> extends EntityModel<T> {
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float age, float yHead, float xHead) { ... }
}
```
**New (1.21.2):**
```java
// 1. Render state class
public class GhostRenderState extends LivingEntityRenderState {
    public boolean isAttacking;
}

// 2. Model now generic over the state
public class GhostModel extends EntityModel<GhostRenderState> {
    public GhostModel(ModelPart root) { super(root); }
    @Override
    public void setupAnim(GhostRenderState state) {
        super.setupAnim(state);       // applies walk/idle/limbSwing from state
        // animate using state.isAttacking etc.
    }
}

// 3. Renderer
public class GhostRenderer extends MobRenderer<GhostEntity, GhostRenderState, GhostModel> {
    public GhostRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GhostModel(ctx.bakeLayer(GHOST_LAYER)), 0.5f);
    }
    @Override
    public ResourceLocation getTextureLocation(GhostRenderState state) { return GHOST_TEX; }
    @Override
    public GhostRenderState createRenderState() { return new GhostRenderState(); }
    @Override
    public void extractRenderState(GhostEntity entity, GhostRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isAttacking = entity.isAttacking();
    }
}
```
**Important:** All per-tick / per-frame entity data must be copied into the render state in
`extractRenderState`. Reading `entity` from `setupAnim` or `getTextureLocation` is no longer
possible after the migration.

### 7.2 `EntityModel.setupAnim` signature
**Primer:** 1.21.2
**Old:** `setupAnim(T entity, float, float, float, float, float)`
**New:** `setupAnim(S state)` — state object carries `walkAnimationPos`, `walkAnimationSpeed`,
`ageInTicks`, `yRot`, `xRot` etc. as fields.

### 7.3 `EntityRenderer#render` → `submit` (1.21.9)
**Primer:** 1.21.9
**Old:** `void render(S state, PoseStack pose, MultiBufferSource buf, int light)`
**New:** `void submit(S state, PoseStack pose, SubmitNodeCollector col, CameraRenderState cam)`
**What to do:** If you override `render` directly (rare — usually you extend `MobRenderer` or
`LivingEntityRenderer`), rename and re-plumb arguments. `SubmitNodeCollector` has
`submitModel`, `submitModelPart`, `submitText`, etc. — replace your `vertexConsumer.vertex(...)`
chains with `col.submitModel(model, state, pose, renderType, light, OverlayTexture.NO_OVERLAY)`
patterns. Most subclasses of `MobRenderer` need no code change — only the methods you override.

### 7.4 `RenderLayer#render` → `submit`
**Primer:** 1.21.9 (after the 1.21.2 generic switch)
**Old:** `RenderLayer<E, M>.render(PoseStack, MultiBufferSource, int light, E entity, ...)`
**New (after 1.21.2 + 1.21.9):**
`RenderLayer<S, M>.submit(PoseStack, SubmitNodeCollector, int light, S state, float yRot, float xRot)`
Generic over render state, not entity. Add `extractRenderState`-derived fields you need from
the state.

### 7.5 `EntityModel` → `Model` (1.21.9)
**Primer:** 1.21.9
For new model classes, extend `Model<S>` instead of `EntityModel<S>` if the entity is not
strictly humanoid/quadruped. Existing `EntityModel<S>` still works as a subclass of `Model`.

### 7.6 `EntityRendererProvider.Context` — `getItemRenderer` removed
**Primer:** 1.21.4
**Old:** `ctx.getItemRenderer()`
**New:** `ctx.getItemModelResolver()` returns an `ItemModelResolver`. To render an item on the
entity (e.g. grenade item-mesh):
```java
ItemStackRenderState rs = new ItemStackRenderState();
ctx.getItemModelResolver().updateForLiving(rs, stack, ItemDisplayContext.GROUND, false, level, entity, 0);
rs.render(pose, buffers, light, OverlayTexture.NO_OVERLAY);   // pre-1.21.9
// post-1.21.9: rs.submit(pose, collector, light, overlay, outlineColor)
```

### 7.7 Model class subpackage moves (1.21.11)
**Primer:** 1.21.11
Many `net.minecraft.client.model.*` classes moved to subpackages
(`net.minecraft.client.model.monster.*`, etc.). Project-wide import update needed if you
extend or reference vanilla models.

---

## 8. ItemStack Data Components

### 8.1 The base API (`.set`, `.get`, `.has`, `.remove`, `.getOrDefault`, `.update`) is stable
**Primer:** Introduced 1.20.5; survives through 26.1.
```java
stack.set(DataComponents.DAMAGE, 5);
int dmg = stack.getOrDefault(DataComponents.DAMAGE, 0);
boolean has = stack.has(DataComponents.DAMAGE);
stack.remove(DataComponents.DAMAGE);
```

### 8.2 NBT compatibility shim
**Primer:** unchanged
**Old:** `stack.getOrCreateTag().putInt("Foo", 5);`
**New:** `stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, d -> d.update(t -> t.putInt("Foo", 5)));`
Or store as a typed component (preferred).

### 8.3 Components-on-Item.Properties
**Primer:** 1.21.2 (component on properties), 26.1 (`delayedComponent`)
**New:**
```java
new Item(new Item.Properties()
    .component(DataComponents.MAX_DAMAGE, 256)
    .delayedComponent(DataComponents.JUKEBOX_PLAYABLE, ctx -> ...))   // registry-dependent
```

### 8.4 New / renamed standard components touching this mod
**Primers:** 1.21.2 / 1.21.5 / 26.1
| Component | What it replaces | Primer |
|---|---|---|
| `DataComponents.FOOD` + `CONSUMABLE` + `USE_REMAINDER` | `FoodProperties` on Item.Properties | 1.21.2 |
| `DataComponents.TOOL` | `Tier.getSpeed`/etc on item | 1.21.2 |
| `DataComponents.WEAPON` | `SwordItem` attack values | 1.21.5 |
| `DataComponents.BLOCKS_ATTACKS` | shield blocking | 1.21.5 |
| `DataComponents.EQUIPPABLE` | armor slot/asset | 1.21.2 |
| `DataComponents.ENCHANTABLE` | `Tier#getEnchantmentValue` | 1.21.2 |
| `DataComponents.REPAIRABLE` | `Tier#getRepairIngredient` | 1.21.2 |
| `DataComponents.BREAK_SOUND` | `Item#getBreakingSound` | 1.21.5 |
| `DataComponents.TOOLTIP_DISPLAY` | `HIDE_ADDITIONAL_TOOLTIP`, `HIDE_TOOLTIP` | 1.21.5 |
| `DataComponents.DAMAGE_TYPE` | tied to spear / kinetic weapons | 1.21.11 / 26.1 |
| `DataComponents.ATTACK_RANGE` | new in 1.21.11 | 1.21.11 |

### 8.5 Tooltip rendering hook signature
**Primer:** 1.21.5
**Old:** `Item#appendHoverText(ItemStack, Level, List<Component>, TooltipFlag)`
**New:** `Item#appendHoverText(ItemStack, Item.TooltipContext, TooltipDisplay, Consumer<Component>, TooltipFlag)`
**What to do:** Update method signature; call `appender.accept(component)` instead of
`list.add(component)`. The old method is deprecated but still wired through.

---

## 9. MenuType & ContainerData

No breaking changes to `MenuType`, `AbstractContainerMenu`, `Slot`, `ContainerData` through 26.1.
The `MenuType.create(MenuConstructor)` registration pattern continues to work; pair with
NeoForge's `IMenuTypeExtension.create((id, inv, buf) -> ...)` for menus that need to read
extra data from the open packet (still the same API).

---

## 10. Data Generation

### 10.1 RecipeProvider rewrite
**Primer:** 1.21.2
**Old:**
```java
public class MyRecipes extends RecipeProvider {
    public MyRecipes(PackOutput out, CompletableFuture<HolderLookup.Provider> reg) { super(out, reg); }
    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(...).save(output);
    }
}
generator.addProvider(true, new MyRecipes(output, lookup));
```
**New:** Two-class structure. RecipeProvider is no longer a DataProvider — its `Runner` is.
```java
public class MyRecipes extends RecipeProvider {
    public MyRecipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }
    @Override
    protected void buildRecipes() {              // no output arg; `this` is the output
        shaped(RecipeCategory.MISC, Items.RESULT)
            .define('a', Items.APPLE)
            .pattern("a")
            .unlockedBy("has_apple", has(Items.APPLE))
            .save(this);
    }
    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput out, CompletableFuture<HolderLookup.Provider> reg) { super(out, reg); }
        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider reg, RecipeOutput out) {
            return new MyRecipes(reg, out);
        }
        @Override
        public String getName() { return "My Recipes"; }
    }
}
generator.addProvider(true, new MyRecipes.Runner(output, lookup));
```
Builder methods `shaped`, `shapeless`, `smelting`, etc. are now inherited as instance methods,
not static.

### 10.2 Recipe `save(...)` uses `ResourceKey<Recipe<?>>`
**Primer:** 1.21.2
**Old:** `.save(output, new ResourceLocation(MODID, "my_recipe"))`
**New:** `.save(this, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(MODID, "my_recipe")))`

### 10.3 `Ingredient.of` — HolderSet-based
**Primer:** 1.21.2
**Old:** `Ingredient.of(Items.APPLE, Items.BREAD)`, `Ingredient.of(ItemTags.PLANKS)`
**New (still works but resolves via HolderSet):**
```java
Ingredient.of(Items.APPLE, Items.BREAD)                                // direct still ok
Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.PLANKS))  // tag
```
Helper `tag(TagKey<Item>)` exists in `RecipeProvider`: `tag(ItemTags.PLANKS)`.

### 10.4 TagProvider split: `IntrinsicHolderTagsProvider` vs `KeyTagProvider`
**Primer:** 1.21.6
**Old:** `extends BlockTagsProvider { tag(BlockTags.X).add(BLOCK); }` (vanilla wrapped this).
**New:** `BlockTagsProvider`, `ItemTagsProvider` etc. still exist in NeoForge but extend
`IntrinsicHolderTagsProvider<T>`. The key-extractor is now passed in the constructor:
```java
public class MyBlockTags extends BlockTagsProvider {
    public MyBlockTags(PackOutput out, CompletableFuture<HolderLookup.Provider> reg, ExistingFileHelper efh) {
        super(out, reg, MODID, efh);     // same as before in NeoForge
    }
    @Override
    protected void addTags(HolderLookup.Provider reg) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(MyBlocks.SPECTRAL_INFUSER.get());
    }
}
```
For custom registry types (datapack registries — e.g. biome modifiers), use
`KeyTagProvider<T>` whose `tag(TagKey<T>)` returns an appender of `ResourceKey<T>` rather than
the value itself.

### 10.5 BlockStateProvider / ItemModelProvider model-output structure (1.21.4)
**Primer:** 1.21.4
- **Item models moved from** `assets/<ns>/models/item/foo.json` **→**
  `assets/<ns>/items/foo.json` (a "client item" JSON describing which model to use).
- `assets/<ns>/models/item/foo.json` still exists, but is now the geometry; `items/foo.json`
  is the wrapper:
  ```json
  { "model": { "type": "minecraft:model", "model": "modid:item/foo" } }
  ```
- NeoForge's `ItemModelProvider` generates this for you, but if you generated files manually
  or used external tooling, account for the new location.
- `ItemModelProvider#registerBuckets` etc. are still there; double-check generated paths.

### 10.6 BlockLootTableProvider — `dropSelf`, `add` API unchanged
**Primer:** No breaking changes through 26.1. The provider class is now sometimes called
`BlockLootSubProvider`; NeoForge wraps it. Adding loot tables still works the same.

### 10.7 AdvancementProvider — `display(ItemStack ...)` → `display(ItemLike ...)` (or template)
**Primer:** 26.1
The `Advancement.Builder#display(ItemStack icon, ...)` overload now takes `ItemStackTemplate`
or you can pass an `ItemLike`; both compile-time available. Existing display calls usually
still work because the most common overload takes `ItemLike`.

### 10.8 DatapackProvider — `Bootstap` → `BootstrapContext` rename
**Primer:** 1.21.2 (and earlier)
**Old:** `BootstapContext<T>` (typo).
**New:** `BootstrapContext<T>`.
**What to do:** Update imports and method signatures of your worldgen registry bootstraps.

### 10.9 SmithingTrimRecipeBuilder takes a Holder
**Primer:** 1.21.5
**Old:** referenced `TrimPatterns.X` registry ref directly.
**New:** Pass `registries.lookupOrThrow(Registries.TRIM_PATTERN).getOrThrow(TrimPatterns.X)`.

---

## 11. World Generation

### 11.1 ConfiguredFeature / PlacedFeature registration mostly unchanged
**Primer:** 1.21.2
Datapack registry bootstraps still use `BootstrapContext`. Key API still:
```java
context.register(MY_CF_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(...)));
```
**Change:** `HolderGetter<ConfiguredFeature<?,?>> cfs = context.lookup(Registries.CONFIGURED_FEATURE);`
to reference one CF from another.

### 11.2 BiomeModifier JSON unchanged
NeoForge's `BiomeModifier` data system (the JSON files in `data/<ns>/neoforge/biome_modifier/`)
continues to work. The codec for `AddFeaturesBiomeModifier` is unchanged. Continue using:
```json
{
  "type": "neoforge:add_features",
  "biomes": "#minecraft:is_overworld",
  "features": "modid:ore_xyz_placed",
  "step": "underground_ores"
}
```

### 11.3 WeightedList replaces SimpleWeightedRandomList (1.21.5)
**Primer:** 1.21.5
**Old:** `SimpleWeightedRandomList.<BlockState>builder().add(state, 10).build()`
**New:** `new WeightedList<>(List.of(new Weighted<>(state, 10)))`.
Affected: `WeightedStateProvider` (use new ctor), `MobSpawnSettings#getMobs` returns
`WeightedList`, etc. If you generate ore configs with weighted state providers, update.

### 11.4 OreConfiguration unchanged
`new OreConfiguration(targetList, veinSize)` and `OreFeatures.STONE_ORE_REPLACEABLES` /
`DEEPSLATE_ORE_REPLACEABLES` constants are intact.

---

## 12. Particles

### 12.1 `SimpleParticleType` registration unchanged
**Primer:** through 26.1
```java
public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);
public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MY_PARTICLE =
    PARTICLES.register("my", () -> new SimpleParticleType(true));
```

### 12.2 `Level.addParticle` — same signature
**Primer:** unchanged through 26.1
```java
level.addParticle(MY_PARTICLE.get(), x, y, z, vx, vy, vz);
```
(Note: 1.21.4 documentation mentioning a new boolean overload refers to internal vanilla
methods, not the standard `addParticle`.) ServerLevel still uses `sendParticles(...)`.

### 12.3 Custom particle renderer rewrite (1.21.9)
**Primer:** 1.21.9
If you have a custom particle class (not just `SimpleParticleType`), it changed dramatically:
- `TextureSheetParticle` was consolidated into `SingleQuadParticle`.
- `Particle#render(VertexConsumer, ...)` → `Particle#extract(QuadParticleRenderState)`.
- `Particle#getRenderType()` → `Particle#getLayer()` returning `SingleQuadParticle.Layer`.
**Action:** Only matters if you have a custom Particle subclass. If you use SimpleParticleType
+ vanilla particle providers, **no migration needed**.

### 12.4 `ItemParticleOption` → takes ItemStack/Item directly
**Primer:** 26.1 (mentions ItemStackTemplate, but ItemStack overload still works in NeoForge)
For `ParticleTypes.ITEM`: `new ItemParticleOption(ParticleTypes.ITEM, itemStack)` continues to
compile in NeoForge through 26.1.

---

## 13. Events (game bus & mod bus)

### 13.1 `@Mod.EventBusSubscriber` → `@EventBusSubscriber`
**Primer:** standardized in NeoForge 1.21.x line
**Old:** `@Mod.EventBusSubscriber(modid = "modid", bus = Mod.EventBusSubscriber.Bus.MOD)`
**New:** `@EventBusSubscriber(modid = "modid", bus = EventBusSubscriber.Bus.MOD)`
Import `net.neoforged.fml.common.EventBusSubscriber`.

### 13.2 ModBus is now passed into mod constructor
**Primer:** NeoForge 1.21.x baseline (you should already do this on 21.1.219)
**Old:** `FMLJavaModLoadingContext.get().getModEventBus()`
**New:** `@Mod(MODID) public class MyMod { public MyMod(IEventBus modBus, ModContainer container) {...} }`

### 13.3 `LivingDamageEvent` split
**Primer:** NeoForge 1.21.x (pre-26.1)
**Old:** `LivingDamageEvent` had two phases conflated.
**New:** Two events:
- `LivingDamageEvent.Pre` — fires before damage is applied; mutable amount/source.
- `LivingDamageEvent.Post` — fires after damage applied, immutable.
**What to do:** Replace `@SubscribeEvent onDamage(LivingDamageEvent e)` with the appropriate
phase. If you cancel damage or reduce it, use `.Pre`. Use `event.getNewDamage()` /
`event.setNewDamage(...)` on Pre.

### 13.4 `PlayerInteractEvent` — cancellation result
**Primer:** 1.21.2
**Old:** `event.setCanceled(true)`
**New:** `event.setCancellationResult(InteractionResult.FAIL)` (still works to setCanceled, but
the result is what propagates).

### 13.5 LivingHurtEvent removed (folded into LivingDamageEvent.Pre)
**Primer:** NeoForge 1.21.x
**Old:** `LivingHurtEvent` modified damage before armor.
**New:** Use `LivingIncomingDamageEvent` (pre-armor) and `LivingDamageEvent.Pre` (post-armor).

### 13.6 BlockEvent listeners — `level` is now `LevelAccessor` consistently
**Primer:** No new break; verify your handlers don't cast to `Level` blindly.

---

## 14. Custom CriterionTrigger

### 14.1 `SimpleCriterionTrigger` validate signature
**Primer:** 26.1
**Old:**
```java
public class MyTrigger extends SimpleCriterionTrigger<MyTrigger.Instance> {
    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        // validate inherited
    }
}
```
**New (26.1):** `CriterionTriggerInstance#validate` now takes `ValidationContextSource`
(or no arg variant via `Validatable`). If you implement `validate(CriterionValidator)`
manually, switch to:
```java
@Override
public void validate(ValidationContextSource ctxSource) {
    ValidationContext ctx = ctxSource.context(LootContextParamSets.ALL_PARAMS);
    // ...
}
```
The standard `SimpleInstance` provides a default impl; in most cases **no code change needed**
for trigger instances that just store a player predicate.

### 14.2 `advancements.critereon` package
**Primer:** 1.21.11
Some classes moved from `net.minecraft.advancements.critereon.*` to
`net.minecraft.advancements.criterion.*` (typo fix). Update imports.

### 14.3 Triggering by ResourceKey
**Primer:** 1.21.2
**Old:** `RecipeCraftedTrigger.TriggerInstance.craftedItem(new ResourceLocation("foo", "bar"))`
**New:** `craftedItem(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath("foo", "bar")))`

---

## 15. NeoForge ModConfig

### 15.1 No breaking changes
**Primer:** through 26.1
`ModConfigSpec.Builder`, `ModConfigSpec.IntValue`, `register*Config(ModConfig.Type, spec)`
all unchanged. `ModConfigEvent.Loading` / `Reloading` still fire on the mod bus.

### 15.2 Cloth Config — external library
Out of scope; track Cloth Config's own changelog for 1.21.11 / 26.1.

---

## 16. ChunkPos / RandomSource / Misc Math

### 16.1 `ChunkPos` API unchanged
**Primer:** through 26.1
`new ChunkPos(x, z)`, `new ChunkPos(blockPos)`, `pos.getMinBlockX()` etc. all still work.
The constructor `new ChunkPos(BlockPos)` is **not** removed — `ChunkPos.containing(blockPos)`
is an alternative that exists but neither is mandatory.

### 16.2 `Level#random` field → `Level#getRandom()` method
**Primer:** 26.1
**Old:** `level.random.nextInt(10)` (public field on `Level`)
**New:** `level.getRandom().nextInt(10)` — field access removed (or changed visibility).
**What to do:** Search for `\.random\.` direct field access on `Level`, `LevelReader`, and
replace with `.getRandom()`. For `RandomSource` parameter usage in features etc., no change.

### 16.3 `RandomSource` API unchanged
`RandomSource.create()`, `RandomSource.create(seed)`, `nextInt`, `nextFloat`, `nextGaussian` all
intact.

### 16.4 `Level#getDayTime` / `setDayTime` (26.1 clock manager)
**Primer:** 26.1
**Old:** `level.getDayTime()`
**New:** Still exists for backwards compat but the canonical access goes through
`level.clockManager().getTotalTicks(clockHolder)`. For most mod code, `getDayTime()` continues
to work.

---

## 17. NBT / CompoundTag API (touches ItemStack, BlockEntity, Entity)

### 17.1 Tag classes are records / sealed
**Primer:** 1.21.5
- `ByteTag`, `ShortTag`, `IntTag`, `LongTag`, `FloatTag`, `DoubleTag`, `StringTag`, `EndTag` →
  records. `new IntTag(5)` no longer compiles — use `IntTag.valueOf(5)`.
- `getAsInt()` → `intValue()` on `NumericTag`. Same for `asLong`, `asFloat`, `asDouble`, `asByte`.
- `Tag#getAsString()` returns `Optional<String>` (was raw `String`). Use `.asString().orElse("")`.

### 17.2 CompoundTag Optional-returning getters (1.21.6)
**Primer:** 1.21.6
**Old:** `int v = tag.getInt("k")` (returned 0 on miss)
**New:** `Optional<Integer> v = tag.getInt("k")` or use `int v = tag.getIntOr("k", 0)`.
Same for `getString`, `getDouble`, `getCompound`, etc.
**What to do:** Audit every `tag.getInt/getString/getCompound` call in NBT-handling code (BE
load, Entity load, item NBT). Switch to the `*Or` variants for one-line migration.

### 17.3 ListTag iteration
**Primer:** 1.21.5
**Old:** `listTag.getCompound(i)` returned a CompoundTag.
**New:** Use `listTag.compoundStream().forEach(...)` or `listTag.getCompoundOrEmpty(i)`.

---

## 18. NeoForge Mod Lifecycle (cross-cutting)

### 18.1 `mods.toml` → `neoforge.mods.toml`
**Primer:** baseline 1.21.x
**Old path:** `META-INF/mods.toml`
**New path:** `META-INF/neoforge.mods.toml`. The format is unchanged. (If your repo is on
21.1.219, you may already be on this path. Confirm.)

### 18.2 `gradle.properties` properties (informational)
Update `minecraft_version`, `neo_version` (from `21.1.219` → e.g. `26.1.x`), `loader_version_range`,
`neo_version_range`, `mod_loader=javafml` accordingly. Update Parchment mappings to the 1.21.11
or 1.21 mappings supported by Parchment for your target.

---

## 19. Saved Data (Per-World Storage)

### 19.1 `SavedData` registration
**Primer:** 1.21.5
**Old:** `data.computeIfAbsent(MyData::load, MyData::new, "mydata")`
**New:** `data.computeIfAbsent(MyData.TYPE)` where `MyData.TYPE` is a `SavedDataType<MyData>`:
```java
public static final SavedDataType<MyData> TYPE = new SavedDataType<>(
    "mydata",
    ctx -> new MyData(),
    ctx -> Codec.INT.fieldOf("value").xmap(MyData::new, d -> d.value),
    DataFixTypes.LEVEL);
```
Only relevant if this mod uses any per-world `SavedData` (e.g. for global mod state).

---

## 20. Sound / VFX glue

### 20.1 `Item#getBreakingSound()` removed
**Primer:** 1.21.5
**Old:** Override `getBreakingSound()`.
**New:** Set `DataComponents.BREAK_SOUND` component via Item.Properties.

### 20.2 `Level#playSound` etc. unchanged
Sound spawning APIs from `Level` are unaffected.

---

## 21. ItemStackTemplate (preview — 26.1)

### 21.1 Internal use
**Primer:** 26.1
Some recipes and advancements now store `ItemStackTemplate` instead of `ItemStack` for
results/icons. The public mod-facing builder API still accepts `ItemLike` / `ItemStack` and
wraps it. Watch for:
- `Recipe#assemble(input)` no longer takes `HolderLookup.Provider`.
- `ItemParticleOption` may have a template overload (the ItemStack overload remains).
You generally do **not** need to construct `ItemStackTemplate` by hand.

---

# Top 10 friction areas for THIS mod

These are ranked by amount of code that needs touching, not by intellectual difficulty.

1. **Custom tool subclasses (Sword/Pickaxe/Shovel/Axe/Hoe) and ArmorItem subclasses must be
   rewritten.** The classes are gone in 1.21.5. Each weapon/tool/armor becomes a plain `Item`
   with `.sword()`/`.pickaxe()`/`.humanoidArmor()` properties + components. If you have
   behaviour overrides (e.g. on-hit effects), move them into either component-driven hooks
   (BLOCKS_ATTACKS, WEAPON) or a custom `Item` subclass.

2. **ToolMaterial / ArmorMaterial reconstruction.** Every custom `Tier` and every custom
   `ArmorMaterial` must be rebuilt — `Tier` becomes `ToolMaterial`, `ArmorMaterial` requires a
   `ResourceKey<EquipmentAsset>` plus a JSON equipment-asset file and new texture paths
   (`textures/entity/equipment/humanoid/<name>.png` instead of `textures/models/armor/...`).

3. **Properties `.setId(ResourceKey)` requirement and `DeferredRegister` helpers.** Every
   item, block, and a few other registry entries need their Properties' id set. The cleanest
   port is to switch all `ITEMS.register("foo", () -> new Item(new Item.Properties()))` to
   `ITEMS.registerItem("foo", Item::new)` and similar for blocks. Touches every registry file.

4. **SpectralInfuser screen / `AbstractContainerScreen` rendering migration.** The
   `GuiGraphics#blit` signature changed (new sampler form + 256/256 size args), `drawString`
   colors must include alpha, `renderTooltip` becomes `setTooltipForNextFrame`, and the
   1.21.6 two-phase pipeline subtly reorders draw calls. Every blit call needs updating; the
   progress arrow and fire icon code in your AbstractContainerScreen subclass will not
   compile and may render incorrectly after the basic fix until tested.

5. **Entity renderer / model rewrite to the RenderState pattern (1.21.2) — GhostEntity and
   GrenadeEntity.** Every custom EntityRenderer must add a RenderState class, override
   `createRenderState` and `extractRenderState`, and convert `setupAnim` to take the state.
   `getTextureLocation` now receives the state, not the entity. Any per-entity flag read
   during render must be copied into the state in `extractRenderState`.

6. **Block#use → useItemOn / useWithoutItem split (1.21.2).** Your SpectralInfuserBlock's
   right-click handler that opens the menu must move from `use(...)` to `useWithoutItem(...)`
   (or `useItemOn` if item-aware). Return type and result constants changed.

7. **NBT (CompoundTag → ValueInput/ValueOutput) for BlockEntity + Entity (1.21.6).** Every
   `saveAdditional`/`loadAdditional` on the SpectralInfuserBlockEntity, every entity custom
   `addAdditionalSaveData`/`readAdditionalSaveData` on Ghost/Grenade has to switch parameter
   types and adopt the `getIntOr("k", 0)` idiom (or `Optional<Integer>` returns). Container
   serialization helpers also lost their `HolderLookup.Provider` argument.

8. **Data generation (RecipeProvider) refactor (1.21.2).** Every recipe provider needs a
   `Runner` inner class and the builder methods become instance methods. Item-model output
   moved to `assets/<ns>/items/*.json` (1.21.4); confirm your `ItemModelProvider` writes the
   new structure. `BootstapContext` → `BootstrapContext` typo fix.

9. **Event renames (`LivingDamageEvent.Pre/Post`, `@Mod.EventBusSubscriber` →
   `@EventBusSubscriber`, `LivingHurtEvent` removal).** Every game-bus event handler must be
   reviewed. The `Pre`/`Post` split for `LivingDamageEvent` is the most error-prone — code that
   reads damage and mutates it must move to `.Pre`.

10. **GUI rendering color/alpha and pose() type change (1.21.6).** Any custom drawing in the
    GUI that uses `gg.drawString(font, text, x, y, 0xFFFFFF)` will draw invisibly because the
    alpha byte is 0. `gg.pose()` is now a `Matrix3x2fStack`, not a `PoseStack`, breaking any
    code that pushes a 3D translate/rotate for HUD elements. Audit every HUD/screen overlay.

---

## Quick reference: search patterns to grep your codebase

| Find | Reason |
|---|---|
| `new SwordItem\|new PickaxeItem\|new ShovelItem\|new AxeItem\|new HoeItem\|new ArmorItem` | §2, §3 — replace with Item + properties |
| `extends SwordItem\|extends PickaxeItem\|extends ArmorItem` | Custom subclasses to refactor |
| `implements Tier` | §2.1 — replace with ToolMaterial |
| `ArmorMaterials\.` | §3 — material refactor |
| `new ResourceLocation\(` | §1.2 — use `fromNamespaceAndPath` |
| `gg.blit(` or `guiGraphics.blit(` | §5.4 — new sig |
| `gg.drawString(.*0x[0-9A-Fa-f]{6}\b` | §5.3 — missing alpha byte |
| `\.use\(BlockState\|public InteractionResult use\(` | §4.8 — useItemOn / useWithoutItem |
| `LivingHurtEvent\|LivingDamageEvent` | §13.3 — phase split |
| `@Mod\.EventBusSubscriber` | §13.1 — rename |
| `Tag .* = tag\.getInt\|getString\|getCompound` | §17.2 — Optional/`*Or` migration |
| `extends EntityModel<` | §7.1 — generic now RenderState |
| `getTextureLocation\(.*Entity ` | §7.1 — now takes RenderState |
| `extends EntityRenderer<\|extends MobRenderer<` | §7.1 — add RenderState generic |
| `level\.random\.\|levelAccessor\.random\.` | §16.2 — switch to `.getRandom()` |
| `RecipeOutput.*buildRecipes\|extends RecipeProvider` | §10.1 — Runner inner class |
| `BootstapContext` | §10.8 — typo fix |
| `saveAdditional\(CompoundTag\|loadAdditional\(CompoundTag\|addAdditionalSaveData\(CompoundTag\|readAdditionalSaveData\(CompoundTag` | §17, §6.4 — ValueOutput/ValueInput |
| `\.getOrCreateTag\(\)` | §8.2 — CUSTOM_DATA component |
| `litTime\b\|litDuration\b\|cookingProgress\b` | §4.4 — furnace field renames |
| `appendHoverText.*Level\|appendHoverText.*List<Component>` | §8.5 — new sig |
| `ResourceLocation\.` (post 1.21.11) | §1.3 — possibly rename to Identifier |
| `Mod\.EventBusSubscriber\.Bus\.` | §13.1 — drop `Mod.` |
| `entityInside\(BlockState .*, Entity entity\)` | §4.6 — new bool param |
