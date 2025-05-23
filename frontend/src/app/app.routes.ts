// app.routes.ts
import { Routes } from '@angular/router';

// Import your components
import { HomeComponent } from './components/home/home.component';

// Import guards
import { AuthGuard } from './guards/auth.guard';
import {ProductComponent} from "./components/product/product.component";

// Define routes
export const routes: Routes = [
  { path: 'home', component: HomeComponent, title: 'Home' , canActivate: [AuthGuard]},
  { path: 'product', component: ProductComponent, title: 'Product' , canActivate: [AuthGuard]},
  // { path: 'about', component: AboutComponent, title: 'About Us' },
  // { path: 'contact', component: ContactComponent, title: 'Contact Us' },
  // {
  //   path: 'profile',
  //   component: ProfileComponent,
  //   canActivate: [authGuard],
  //   title: 'User Profile'
  // },
  // {
  //   path: 'admin',
  //   loadChildren: () => import('./admin/admin.routes').then(m => m.ADMIN_ROUTES),
  //   canMatch: [adminGuard]
  // },
  // {
  //   path: 'products',
  //   loadChildren: () => import('./products/products.routes').then(m => m.PRODUCT_ROUTES)
  // },
  // // Wildcard route for 404 page
  // { path: '**', component: NotFoundComponent, title: 'Page Not Found' }
];
