package at.petrak.hexcasting.common.casting.operators

import at.petrak.hexcasting.api.misc.ManaConstants
import at.petrak.hexcasting.api.spell.Action
import at.petrak.hexcasting.api.spell.ConstManaAction
import at.petrak.hexcasting.api.spell.asActionResult
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getVec3
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.iota.NullIota
import net.minecraft.world.level.ClipContext
import net.minecraft.world.phys.HitResult

object OpBlockAxisRaycast : ConstManaAction {
    override val argc = 2
    override val manaCost = ManaConstants.DUST_UNIT / 100
    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val origin = args.getVec3(0, argc)
        val look = args.getVec3(1, argc)

        val blockHitResult = ctx.world.clip(
            ClipContext(
                origin,
                Action.raycastEnd(origin, look),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                ctx.caster
            )
        )

        return if (blockHitResult.type == HitResult.Type.BLOCK) {
            blockHitResult.direction.step().asActionResult
        } else {
            listOf(NullIota())
        }
    }
}
