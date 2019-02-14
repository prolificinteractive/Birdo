package com.prolificinteractive.birdo.modules

import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.prolificinteractive.birdo.R
import io.palaima.debugdrawer.actions.Action

/**
 * Custom switch action that doesn't rely on shared preferences to save its state.
 *
 * @param name Action name of the switch.
 * @param listener Callback when checked.
 * @param firstValue First value to set the switch to, default to false.
 */
class CustomSwitchAction(
    private val name: String,
    @Nullable private val listener: Listener,
    private val firstValue: Boolean = false
) : Action {

  private lateinit var switchButton: Switch

  override fun getView(@NonNull inflater: LayoutInflater, @NonNull parent: LinearLayout): View {

    val viewGroup = inflater.inflate(R.layout.dd_debug_drawer_module_actions_switch, parent, false)

    val textView = viewGroup.findViewById<TextView>(R.id.action_switch_name)
    textView.text = name

    switchButton = viewGroup.findViewById(R.id.action_switch_switch)
    switchButton.setOnCheckedChangeListener(null)
    switchButton.isChecked = firstValue
    switchButton.setOnCheckedChangeListener(switchListener)

    return viewGroup
  }

  override fun onOpened() { /* no-op */ }

  override fun onClosed() { /* no-op */ }

  override fun onResume() { /* no-op */ }

  override fun onPause() { /* no-op */ }

  override fun onStart() {
    switchButton.setOnCheckedChangeListener(null)
    switchButton.isChecked = firstValue
    switchButton.setOnCheckedChangeListener(switchListener)
  }

  override fun onStop() { /* no-op */ }

  private val switchListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
    listener.onCheckedChanged(isChecked)
  }

  /**
   * Whether the switch is checked.
   */
  fun isChecked(): Boolean {
    return switchButton.isChecked
  }

  /**
   * Update the status of the swtich.
   */
  fun setChecked(checked: Boolean) {
    switchButton.isChecked = checked
  }

  /**
   * Callback when the switch value changes.
   */
  interface Listener {
    /**
     * Called when the switch value is changed.
     *
     * @param value Whether the switch action is checked.
     */
    fun onCheckedChanged(value: Boolean)
  }
}