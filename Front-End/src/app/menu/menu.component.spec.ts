import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule }               from '@angular/router/testing';
import { MenuComponent }                     from './menu.component';
import { AppModule }                         from '../app.module';
import { CUSTOM_ELEMENTS_SCHEMA }            from '@angular/core';

describe('MenuComponent', () => {
  let component: MenuComponent;
  let fixture: ComponentFixture<MenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        AppModule
      ],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
