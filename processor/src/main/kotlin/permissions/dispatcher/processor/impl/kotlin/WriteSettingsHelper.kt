package permissions.dispatcher.processor.impl.kotlin

import com.squareup.javapoet.ClassName
import com.squareup.kotlinpoet.FunSpec

class WriteSettingsHelper : SensitivePermissionInterface {

    private val PERMISSION_UTILS = ClassName.get("permissions.dispatcher", "PermissionUtils")
    private val SETTINGS = ClassName.get("android.provider", "Settings")
    private val INTENT = ClassName.get("android.content", "Intent")
    private val URI = ClassName.get("android.net", "Uri")

    override fun addHasSelfPermissionsCondition(builder: FunSpec.Builder, activity: String, permissionField: String) {
        builder.beginControlFlow("if (%T.hasSelfPermissions(%N, %N) || %T.System.canWrite(%N))", PERMISSION_UTILS, activity, permissionField, SETTINGS, activity)
    }

    override fun addRequestPermissionsStatement(builder: FunSpec.Builder, activity: String, requestCodeField: String) {
        builder.addStatement("%T intent = %T(%T.ACTION_MANAGE_WRITE_SETTINGS, %T.parse(\"package:\" + %N.getPackageName()))", INTENT, INTENT, SETTINGS, URI, activity)
        builder.addStatement("%N.startActivityForResult(intent, %N)", activity, requestCodeField)
    }
}