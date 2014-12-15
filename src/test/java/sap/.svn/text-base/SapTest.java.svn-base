package sap;

import static com.hengyi.japp.sap.Constant.ZFIND_ALL_T077X;
import static com.hengyi.japp.sap.SapUtil.convert;

import com.hengyi.japp.sap.BaseSapService;
import com.hengyi.japp.sap.DestinationType;
import com.hengyi.japp.sap.convert.impl.SapConverts;
import com.hengyi.japp.sap.dto.SapT077xDTO;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class SapTest {

	public static void main(String[] args) throws Exception {
		BaseSapService sf = new BaseSapService() {

			@Override
			protected DestinationType getDestinationType() {
				return DestinationType.DEV;
			}

		};
		JCoDestination dest = sf.getDestination();
		JCoFunction f = sf.getFunction("RFC_READ_TABLE", dest);
		f.getImportParameterList().setValue("QUERY_TABLE", "T077X");
		f = sf.getFunction(ZFIND_ALL_T077X, dest);
		f.execute(dest);
		JCoTable t = f.getTableParameterList().getTable("ET_T077X");
//		SapConverts.begin().addRecord(t).addBeanClass(SapT077xDTO.class).build().convert(clazz, table)
		convert(SapT077xDTO.class, t);
	}
}
