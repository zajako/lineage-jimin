package bone.server.Warehouse;

public class ClanWarehouseList extends WarehouseList {
	@Override
	protected ClanWarehouse createWarehouse(String name) {
		return new ClanWarehouse(name);
	}
}