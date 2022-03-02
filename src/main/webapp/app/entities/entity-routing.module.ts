import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'raw-material-order',
        data: { pageTitle: 'aquigenBioApp.rawMaterialOrder.home.title' },
        loadChildren: () => import('./raw-material-order/raw-material-order.module').then(m => m.RawMaterialOrderModule),
      },
      {
        path: 'categories',
        data: { pageTitle: 'aquigenBioApp.categories.home.title' },
        loadChildren: () => import('./categories/categories.module').then(m => m.CategoriesModule),
      },
      {
        path: 'unit',
        data: { pageTitle: 'aquigenBioApp.unit.home.title' },
        loadChildren: () => import('./unit/unit.module').then(m => m.UnitModule),
      },
      {
        path: 'consumption-details',
        data: { pageTitle: 'aquigenBioApp.consumptionDetails.home.title' },
        loadChildren: () => import('./consumption-details/consumption-details.module').then(m => m.ConsumptionDetailsModule),
      },
      {
        path: 'transfer',
        data: { pageTitle: 'aquigenBioApp.transfer.home.title' },
        loadChildren: () => import('./transfer/transfer.module').then(m => m.TransferModule),
      },
      {
        path: 'transfer-details',
        data: { pageTitle: 'aquigenBioApp.transferDetails.home.title' },
        loadChildren: () => import('./transfer-details/transfer-details.module').then(m => m.TransferDetailsModule),
      },
      {
        path: 'tranfer-details-approvals',
        data: { pageTitle: 'aquigenBioApp.tranferDetailsApprovals.home.title' },
        loadChildren: () =>
          import('./tranfer-details-approvals/tranfer-details-approvals.module').then(m => m.TranferDetailsApprovalsModule),
      },
      {
        path: 'tranfer-recieved',
        data: { pageTitle: 'aquigenBioApp.tranferRecieved.home.title' },
        loadChildren: () => import('./tranfer-recieved/tranfer-recieved.module').then(m => m.TranferRecievedModule),
      },
      {
        path: 'purchase-order',
        data: { pageTitle: 'aquigenBioApp.purchaseOrder.home.title' },
        loadChildren: () => import('./purchase-order/purchase-order.module').then(m => m.PurchaseOrderModule),
      },
      {
        path: 'purchase-order-details',
        data: { pageTitle: 'aquigenBioApp.purchaseOrderDetails.home.title' },
        loadChildren: () => import('./purchase-order-details/purchase-order-details.module').then(m => m.PurchaseOrderDetailsModule),
      },
      {
        path: 'goods-recived',
        data: { pageTitle: 'aquigenBioApp.goodsRecived.home.title' },
        loadChildren: () => import('./goods-recived/goods-recived.module').then(m => m.GoodsRecivedModule),
      },
      {
        path: 'warehouse',
        data: { pageTitle: 'aquigenBioApp.warehouse.home.title' },
        loadChildren: () => import('./warehouse/warehouse.module').then(m => m.WarehouseModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'aquigenBioApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'product-inventory',
        data: { pageTitle: 'aquigenBioApp.productInventory.home.title' },
        loadChildren: () => import('./product-inventory/product-inventory.module').then(m => m.ProductInventoryModule),
      },
      {
        path: 'product-transaction',
        data: { pageTitle: 'aquigenBioApp.productTransaction.home.title' },
        loadChildren: () => import('./product-transaction/product-transaction.module').then(m => m.ProductTransactionModule),
      },
      {
        path: 'security-user',
        data: { pageTitle: 'aquigenBioApp.securityUser.home.title' },
        loadChildren: () => import('./security-user/security-user.module').then(m => m.SecurityUserModule),
      },
      {
        path: 'product-quatation',
        data: { pageTitle: 'aquigenBioApp.productQuatation.home.title' },
        loadChildren: () => import('./product-quatation/product-quatation.module').then(m => m.ProductQuatationModule),
      },
      {
        path: 'quatation-details',
        data: { pageTitle: 'aquigenBioApp.quatationDetails.home.title' },
        loadChildren: () => import('./quatation-details/quatation-details.module').then(m => m.QuatationDetailsModule),
      },
      {
        path: 'user-access',
        data: { pageTitle: 'aquigenBioApp.userAccess.home.title' },
        loadChildren: () => import('./user-access/user-access.module').then(m => m.UserAccessModule),
      },
      {
        path: 'security-role',
        data: { pageTitle: 'aquigenBioApp.securityRole.home.title' },
        loadChildren: () => import('./security-role/security-role.module').then(m => m.SecurityRoleModule),
      },
      {
        path: 'security-permission',
        data: { pageTitle: 'aquigenBioApp.securityPermission.home.title' },
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.SecurityPermissionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
