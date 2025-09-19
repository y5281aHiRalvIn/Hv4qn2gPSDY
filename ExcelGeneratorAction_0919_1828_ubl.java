// 代码生成时间: 2025-09-19 18:28:22
package com.example.struts.actions;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelGeneratorAction extends DispatchAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            return generateExcel(mapping, form, request, response);
        } catch (Exception e) {
            // Log the exception and return an error message
            e.printStackTrace();
            // You can also set an error message in the request
            return mapping.findForward("error");
        }
    }

    /**
     * Method to generate and download Excel file.
     *
     * @param mapping ActionMapping
     * @param form    ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     *
     * @throws IOException
     */
    private ActionForward generateExcel(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Set the response content type
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Set the header field
        response.setHeader("Content-Disposition", "attachment; filename=GeneratedExcel.xlsx");

        // Create a workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Generated Data");

        // Create a row
        Row row = sheet.createRow(0);

        // Create a cell style
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        // Create cells and set values
        Cell cell = row.createCell(0);
        cell.setCellValue("Column 1");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("Column 2");
        cell.setCellStyle(cellStyle);

        // Add sample data to the sheet
        List<List<Object>> data = new ArrayList<>();
        // Populate the data list as needed
        // For example, data.add(Arrays.asList("Value1", "Value2"));

        for (int i = 1; i <= data.size(); i++) {
            row = sheet.createRow(i);
            for (int j = 0; j < data.get(i - 1).size(); j++) {
                cell = row.createCell(j);
                cell.setCellValue(data.get(i - 1).get(j).toString());
            }
        }

        // Write the workbook content to the OutputStream
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        // Return null to indicate that the response has already been handled
        return null;
    }
}