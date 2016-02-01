package formats.siren


import com.sungevity.commons.formats.siren.spec._
/**
  * Created on 1/29/16.
  */
package object inverter {

    val uuidIdentifiedClass: ClassSpec =
      ClassSpec("uuid-identified","Entities that are identified with a version 4 UUID",None,Set(
        PropertySpec("id","UUID",true,
          "A Version-4 UUID that identifies persistent entities.",None,"String")),Set())

    val inverterProductClass: ClassSpec =
      ClassSpec("inverter-product","Inverter products model the various kinds of inverters we offer to customers.",None,
        Set(
          PropertySpec("name","modelName",true,
            "The model name for this inverter product.",None,"String"),
          PropertySpec("type","manufacturerName",true,
            "The manufacturer for this inverter product.",None,"String"),
          PropertySpec("description","Description",true,"The description of the inverter product.",None,"String"),
        PropertySpec("modifiedDate","Modified Date",true,
          "The date the inverter was modified.",None,"Int"),
        PropertySpec("rating","Rating",true,"Rating associated with inverter product.",None,"Double"),
        PropertySpec("efficiency","Efficiency",true,
          "A floating point number between 0 and 1 representing the efficiency of the inverter output", None,
            "Double")),Set())

  val titleClass: ClassSpec =
    ClassSpec("title","Title",None,Set(
      PropertySpec("title","Title",true,
        "ManufacturerName and modelName.",None,"String")),Set())


    val inverterProductSoloEntitySpec: EntitySpec =
      EntitySpec("inverterProduct", "An inverter product without sub-entities.", None,
        Set(
          titleClass,
          inverterProductClass,
          uuidIdentifiedClass
        )
      )

  }

package object module {
  val uuidIdentifiedClass: ClassSpec =
    ClassSpec("uuid-identified","Entities that are identified with a version 4 UUID",None,Set(
      PropertySpec("id","UUID",true,
        "A Version-4 UUID that identifies persistent entities.",None,"String")),Set())

  val moduleProductClass: ClassSpec =
    ClassSpec("module-product","The various kinds of modules we offer to customers.",None,
      Set(
        PropertySpec("name","modelName",true,
          "The model name for this inverter product.",None,"String"),
        PropertySpec("type","manufacturerName",true,
          "The manufacturer for this module product.",None,"String"),
        PropertySpec("description","Description",true,"The description of the module product.",None,"String"),
        PropertySpec("modifiedDate","Modified Date",true,
          "The date the inverter was modified.",None,"Int"),
        PropertySpec("kwStc","kwStc",true,"kilowatts produced in standard testing conditions.",None,
          "Double"),
        PropertySpec("kwPtc","kwPtc",true,"kilowatts produced in real testing conditions .",None,"Double"),
        PropertySpec("height","Height mm",true,"height of module in mm.",None,"Double"),
        PropertySpec("width","Width mm",true,"width of module in mm..",None,"Double"),
        PropertySpec("isBipvRated","Bipv Rating",true,"Rating associated with sustainable homes.",None,"Boolean"),
        PropertySpec("powerTemperatureCoefficient","Power Temperature Coefficient",true,"coefficient associated with" +
          "in normal conditions.",None, "Double"),
        PropertySpec("normalOperatingCellTemp","Normal Operating Cell Temperature",true,"temperature of PV cell" +
          "inverter ",None,"Double"),
        PropertySpec("medianPmaxMultiplier","Median Pmax Multiplier",true,"median Pmax multiplier.",
          None,"Double")),Set())

  val titleClass: ClassSpec =
    ClassSpec("title","Title",None,Set(
      PropertySpec("title","Title",true,
        "ManufacturerName and modelName.",None,"String")),Set())


  val moduleProductSoloEntitySpec: EntitySpec =
    EntitySpec("moduleProduct", "A module product without sub-entities.", None,
      Set(
        titleClass,
        moduleProductClass,
        uuidIdentifiedClass
      )
    )
}




